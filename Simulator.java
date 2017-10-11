import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.System;
import java.util.*;

/**
 * Simulate a simulation.  A simulator object maintains a priority queue
 * of events, and repeatedly process the events in the queue until the
 * time is up.  We can schedule an event in the simulator with the @code{add}
 * method, and run the simulator with the @code{run} method.
 *
 * <p>A simulator object interacts with Event objects and Simulation object,
 * both handles the actual system that is being simulated.</p>
 */

class Simulator {
  /**
   * A shop encapsulates the states of the simulation (the 
   * servers and the queues).
   */
  private Shop shop;
  /**
   * Parameters required to of the simulation.
   */
  private Params params;
  /**
   * Statistics of the system being simulated.
   */
  public Statistic stats;
  /**
   * The current simulation time.
   */
  private double timeNow;
  /**
   * Random number generator for the simulation.
   */ 
  public static Random rnd = new Random();
  /**
   * A priority queue of events.
   */
  private PriorityQueue<Event> events;
  /**
   * Params is a private container class for all configuration parameters
   * of this simulation.
   */

  private class Params {
    /**
     * The seed for random number generation.
     */
    public int seed;
    /**
     * The number of servers, c.
     */
    public int numServers;
    /**
     * The number of queues, must be either 1 or c.
     */
    public int numQueues;
    /**
     * How long the simulation should last.
     */
    public double simTime;
    /**
     * The service rate (exponentially distributed).
     */
    public double mu;
    /**
     * The arrival rate (exponentially distributed).
     */
    public double lambda;
    /**
     * The probability of a pioneer customer arriving.
     */
    public double pioneerChance;
    /**
     * The probability of a kiasu customer arriving.
     */   
    public double kiasuChance;
    /**
     * The time between switch events (exponentially calculated).
     */
    public double switchTime;
    /**
     * The probability a server goes on break.
     */
    public double breakChance;
    /**
     * The break time (exponentially calculated).
     */
    public double breakTime;

    /**
     * Initialize the parameters either from the standard input or
     * from a given input file (specified by filename).
     * 
     * @param  args  the command line argument.  If none is given, read from
     *     standard input.  Else, assume args[0] is the input filename, and read
     *     from that file.
     */
    public Params(String[] args) {
      Scanner s = null;
      try {
        // Read from stdin if no filename is given, otherwise
        // read from the given file.
        if (args.length == 0) {
          s = new Scanner(System.in);
        } else {
          FileReader f = new FileReader(args[0]);
          s = new Scanner(f);
        }
        seed = s.nextInt();
        numServers = s.nextInt();
        mu = s.nextDouble();
        numQueues = s.nextInt();
        lambda = s.nextDouble();
        simTime = s.nextDouble();
        pioneerChance = s.nextDouble();
        kiasuChance = s.nextDouble();
        switchTime = s.nextDouble();
        breakChance = s.nextDouble();
        breakTime = s.nextDouble();
      } catch (FileNotFoundException ex) {
        System.err.println("Unable to open file " + args[0] + " " + ex + "\n");
        simTime = 0;
      } catch (InputMismatchException ex) {
        System.err.println("Format error in input");
        simTime = 0;
      }
    }
  }

  /**
   * Statistics is a private container class for all statistics
   * collected wrt to this this simulation.
   */
  class Statistic {
    /**
     * The total amount of time waited by all customers.
     */
    private double totalTimeWaited = 0;
    /*
     * The total number of customer served.
     */
    private int totalCustomerServed = 0;

    /**
     * Called to indicate that a customer has been served, after
     * waiting some time (can be 0) in a queue.
     * 
     * @param time the time a customer spent waiting in the queue.
     */
    public void addServingTime(double time) {
      totalTimeWaited += time;
      totalCustomerServed++;
    }

    /**
     * Return a string representation of all the data collected.
     * 
     * @return A string with three numbers: the average waiting time, the
     *     number of customer served, and the number of customer lost.
     */
    public String toString() {
      return String.format("%.3f %d 0", totalTimeWaited / totalCustomerServed,
      totalCustomerServed);
    }
  }

  /**
   * Initialize the parameters and the states of the simulation.
   * 
   * @param args the command line argument.  If none is given, read from
   *     standard input.  Else, assume args[0] is the input filename, and
   *     read from that file.
   */
  public Simulator(String[] args) {
    this.events = new PriorityQueue<Event>();

    stats = new Statistic();
    params = new Params(args);
    timeNow = 0;

    rnd.setSeed(params.seed);
    shop = new Shop(params.numServers, params.numQueues);
  }

  /**
   * Helper method to generate a random value by passing a parameter to
   * a logarithm algorithm.
   * 
   * @param rate Variable modifier for generated values.
   * @return a generated logarithmic value.
   */
  private double exponentialValue(double rate) {
    return -Math.log(rnd.nextDouble()) / rate;
  }

  /**
   * Generate a random inter-arrival time according to exponential
   * distribution with average arrival rate lambda.
   * 
   * @return the randomly generated inter-arrival time.
   */
  private double generateArrivalTime() {
    return exponentialValue(params.lambda);
  }

  /**
   * Generate a random service time according to exponential distribution
   * with average service time mu.
   * 
   * @return the randomly generated service time.
   */
  private double generateServiceTime() {
    return exponentialValue(params.mu);
  }

  /**
   * Generate a random break time according to exponential distribution
   * with parameter break time.
   * 
   * @return the randomly generated break time.
   */
  private double generateBreakTime() {
    return exponentialValue(params.breakTime);
  }

  /**
   * Generate a random time between switch events according to exponential 
   * distribution with parameter switch time.
   *
   * @return the randomly generated time between switch events.
   */
  private double generateSwitchTime() {
    return exponentialValue(params.switchTime);
  }

  /**
   * Generate one of three type of customers: Pioneer, Typical or Kiasu based
   * on parameters pP and pK, where pP denotes the chance a Pioneer customer
   * will be generated and pK denotes the chance a Kiasu customer will be
   * generated. Else, a typical customer is generated instead.
   * 
   * @return a customer of one of three types: Pioneer, Typical or Kiasu.
   */
  public Customer generateCustomer() {   
    double dice = rnd.nextDouble();
    //yay tenary operators!
    return (dice < params.pioneerChance) 
      ? new PioneerCustomer(generateServiceTime())
      : (dice > 1 - params.kiasuChance) 
      ? new KiasuCustomer(generateServiceTime())
      : new TypicalCustomer(generateServiceTime());
  }

  /**
   * Generate one of three type of customers: Pioneer, Typical or Kiasu based
   * on parameters pP and pK, where pP denotes the chance a Pioneer customer
   * will be generated and pK denotes the chance a Kiasu customer will be
   * generated. Else, a typical customer is generated instead.
   * 
   * @return a customer of one of three types: Pioneer, Typical or Kiasu.
   */
  public boolean generateBreakChance() {
    return (rnd.nextDouble() < params.breakChance);
  }

  /**
   * Generate the next random arrival event with a customer requring a
   * random service time.
   * 
   * @return the new arrival event.
   */
  public Event generateArrival() {
    //switching the below two lines will generate wildly different outputs!
    double timeAfter = generateArrivalTime();    
    Customer c = generateCustomer();  
    return new ArrivalEvent(this, this.timeNow + timeAfter, c);
  }

  /**
   * Generate a "done" event indicating that the given server has completed
   * service to the given customer.  The service time is determined by the
   * customer.
   * 
   * @param c the customer served
   * @param s the server who serves the custoner.
   * @return the new done event.
   */
  public Event generateDone(Customer c, Server s) {
    double doneAt = this.timeNow + c.getServiceTime();
    return new DoneEvent(this, doneAt, c, s);
  }

  /**
   * Generate a service event at the current time to signal that the
   * customer is now being served by the server.
   * 
   * @param c the customer being served.
   * @param s the server serving the customer.
   * @return the new serve event.
   */
  public Event generateServe(Customer c, Server s) {
    return new ServeEvent(this, this.timeNow, c, s);
  }

  /**
   * Generate a break event at the current time to signal that the current
   * server is going on break after a done event.
   * 
   * @param s the server going for break.
   * @return the new break event.
   */
  public Event generateBreak(Server s) {
    return new BreakEvent(this, this.timeNow, s);
  }

  /**
   * Generate a resume event after server returns from break from random given
   * break time.
   * 
   * @param s the server returning from break
   * @return the new resume event.
   */
  public Event generateResume(Server s) {
    double breakAt = this.timeNow + generateBreakTime();
    return new ResumeEvent(this, breakAt, s);
  }

  /**
   * Generate a time between switch event for each customer not being served
   * immediately. Switch events are generated atomically for each individual
   * customers until they are served.
   * 
   * @param c the customer switching queues.
   * @return the new switch event.
   */ 
  public Event generateSwitch(Customer c) {
    double moveAt = this.timeNow + generateSwitchTime();
    return new SwitchEvent(this, moveAt, c);
  }

  /**
   * Record the waiting time of a customer.
   *
   * @param time The time a customer spent waiting (could be 0)
   */
  public void recordCustomerServed(double time) {
    stats.addServingTime(time);
  }

  /**
   * Return the first set of events to the simulator to kick-start the
   * simulator.
   *
   * @return An array of initial events to populate the simualtor with.
   */
  public Event[] firstEvents() {
    return new Event[] { generateArrival() };
  }

  /**
   * Schedule a set of events in the simulator so that it is executed later.
   *
   * @param events The event to be simulated.
   */
  public void schedule(Event[] events) {
    this.events.addAll(Arrays.asList(events));
  }

  /**
   * Simulate the occurance of a given event.  More events may be created as
   * a result of this event.  The new events are returned in an array.
   * If the curren simulation time is already beyond the expired time, do
   * nothing.
   *
   * @param e The event to simulate.
   * @return An array of new events created as a result of simulating this
   *     event.
   */
  public Event[] handle(Event e) {
    this.timeNow = e.getTime();
    if (this.timeNow > params.simTime) {
      return null;
    }
    return e.run(shop);
  }

  /**
   * Run the simulator. The simulator keeps polling the next event from the
   * event list, passes the event to the simulation to handle, until the
   * simulation expires(regulated by simTime). Finally, the simulator prints 
   * out the stats.
   */
  public void run() {
    schedule(firstEvents());
    while (!events.isEmpty()) {
      Event e = this.events.poll();
      if (e.happensBefore(params.simTime)) {
        Event[] newEvents = handle(e);
        //e.log();
        if (newEvents != null) {
          this.events.addAll(Arrays.asList(newEvents));
        }
      } else {
        break;
      }
    }
    System.out.println(stats);
  }
}
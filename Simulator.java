import java.io.*;
import java.util.*;

class Simulator{
  private Shop shop;
  private Params params;
  private double timeNow;

  public Statistic stats;
  public static Random rnd = new Random();

  private PriorityQueue<Event> events;

  //Parameter class.
  private class Params {
    public int seed;
    public int numServers;
    public double simTime;
    public int numQueues;
    public double mu;
    public double lambda;
    public double pP;    
    public double pK;
    public double switchTime;
    public double breakChance;
    public double breakTime;

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
        //New lines here.
        pP = s.nextDouble();
        pK = s.nextDouble();
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

  //Statistic class.
  class Statistic {
    private double totalTimeWaited = 0;
    private int totalCustomerServed = 0;

    public void addServingTime(double time){
      totalTimeWaited += time;
      totalCustomerServed++;
    }

    public String toString(){
      return String.format("%.3f %d 0", totalTimeWaited / totalCustomerServed,
      totalCustomerServed);
    }
  }

  public Simulator(String[] args){
    this.events = new PriorityQueue<Event>();

    stats = new Statistic();
    params = new Params(args);
    timeNow = 0;  
    rnd.setSeed(params.seed);

    shop = new Shop(params.numServers, params.numQueues);
  }

  private double generateArrivalTime(){
    return -Math.log(rnd.nextDouble()) / params.lambda;    
  }

  private double generateServiceTime(){
    return -Math.log(rnd.nextDouble()) / params.mu;
  }

  private double generateBreakTime(){
    return -Math.log(rnd.nextDouble()) / params.breakTime;
  }

  private double generateSwitchTime(){
    return -Math.log(rnd.nextDouble()) / params.switchTime;
  }

  private double generatePioneer(){
    return -Math.log(rnd.nextDouble()) / params.pP;
  }

  private double generateKiasu(){
    return -Math.log(rnd.nextDouble()) / params.pK;
  }

  public Customer generateCustomer(){   
    double dice = rnd.nextDouble();
    if (dice < params.pP){
      return new PioneerCustomer(generateServiceTime());
    } else if (dice > 1-params.pK) {
      return new KiasuCustomer(generateServiceTime());
    }else{
      return new TypicalCustomer(generateServiceTime());      
    }
  }

  public boolean generatebreakChance(){
    double dice = rnd.nextDouble();
    if (dice > params.breakChance) {        
      return true;
    } else {
      return false;
    }
  }

  //Generates one of 3 kinds of customers.
  public Event generateArrival(){
    double timeAfter = generateArrivalTime();
    Customer c = generateCustomer();
    //Customer c = new TypicalCustomer(generateServiceTime());
    return new ArrivalEvent(this, this.timeNow + timeAfter, c);
    //TODO - Add customer here can be kiasu or pioneer.
  }

  public Event generateDone(Customer c, Server s){
    //TODO DONEEVENT
    double doneAt = this.timeNow + c.getServiceTime();
    return new DoneEvent(this, doneAt, c, s);
  }

  public Event generateServe(Customer c, Server s){
    return new ServeEvent(this, this.timeNow, c, s);
  }

  public Event generateBreak(Server s){
    return new BreakEvent(this, this.timeNow, s);
  }
  
  public Event generateResume(Server s){
    double breakAt = this.timeNow + generateBreakTime();
    return new ResumeEvent(this, breakAt, s);
  }

  public Event generateSwitch(Customer c){
    double moveAt = this.timeNow + generateSwitchTime();
    return new SwitchEvent(this, moveAt, c);
  }

  public void recordCustomerServed(double time){
    stats.addServingTime(time);
  }

  public Event[] firstEvents() {
    return new Event[] { generateArrival() };
  }
  
  public void schedule(Event[] events) {
    this.events.addAll(Arrays.asList(events));
  }

  public Event[] handle(Event e){
    this.timeNow = e.getTime();
    if(this.timeNow > params.simTime){
      return null;
    }
    return e.run(shop);
  }

  public void run(){
    schedule(firstEvents());
    while(!events.isEmpty()){
      Event e = this.events.poll();
      if(e.happensBefore(params.simTime)){
        Event[] newEvents = handle(e);
        //e.log();
        if(newEvents != null){
          this.events.addAll(Arrays.asList(newEvents));
        }
      }else{
        break;
      }
    }
    System.out.println(stats);
  }
}
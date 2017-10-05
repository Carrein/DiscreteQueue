import java.io.*;
import java.util.*;

class Simulation{
  private Shop shop;
  private Params params;
  private double timeNow;

  public Statistics stats;
  public static Random rnd = new Random();

  private List<Server> servers;
  private List<Queue> queues;
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
        breakLength = s.nextDouble();
      } catch (FileNotFoundException ex) {
        System.err.println("Unable to open file " + args[0] + " " + ex + "\n");
        timeToSimulate = 0;
      } catch (InputMismatchException ex) {
        System.err.println("Format error in input");
        timeToSimulate = 0;
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

  public Simulation(String[] args){
    this.events = new PriorityQueue<Event>();
    stats = new Statistic();
    params = new Params(args);
    timeNow = 0;  
    rnd.setSeed(params.seed);
  }

  private void generateShop(){
    servers = new ArrayList<Server>();
    queues = new ArrayList<Queue>();

    if(params.numQueues == 1){
      Queue q = new Queue();
      queues.add(q);
      for(int i = 0; i < params.numServers; i++){
        servers.add(new Server(q));
      }
    }else{
      for(int i = 0; i < params.numServers; i++){
        Queue q = new Queue();
        queues.add(q);
        servers.add(newServer(q));
      }
    }
  }

  private double generateArrivalTime(){
    return -Math.log(random.nextDouble()) / params.lambda;    
  }

  private double generateServiceTime(){
    return -Math.log(random.nextDouble()) / params.mu;
  }

  public Event generateArrival(){
    double timeAfter = generateArrivalTime();
    //TODO - Add customer here can be kiasu or pionennr.
    //TODO - return new ARRIVALEVENT here
  }

  public generateDone(){
    //TODO DONEEVENT
  }

  public void recordCustomerServed(double time){
    stats.addServingTime(time);
  }

  public Event[] firstEvents(){
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
    //TODO - INSERT PARAMTERS return e.run();
  }

  public void run(){
    generateShop();
    schedule(firstEvents());
    while(!events.isEmpty()){
      break;
      //Add event checking logic.
    }
  }
}
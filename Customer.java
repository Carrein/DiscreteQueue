import java.util.List;

abstract class Customer{
  private final double serviceTime;
  private double timeStartedWaiting;
  private double waitingTime;
  private static int numOfCustomer = 0;
  private int id;

  public Customer(double serviceTime){
    this.serviceTime = serviceTime;
    this.waitingTime = 0;
    this.timeStartedWaiting = 0;
    this.id = Customer.numOfCustomer;
    Customer.numOfCustomer++;
  }

  public void startWaitingAt(double time){
    this.timeStartedWaiting = time;
  }

  public void stopWaitingAt(double now){
    this.waitingTime = now - this.timeStartedWaiting;
  }

  public double getWaitingTime(){
    return this.waitingTime;
  }

  public double getServiceTime(){
    return this.serviceTime;
  }

  public abstract Queue joinQueue(Shop shop);

  public Server findIdleServer(List<Server> servers) {
    for (Server server: servers) {
      if (server.isIdle()) {
        return server;
      }
    }
    return null;
  }

  public String toString() {
    return "C" + id;
  }
}
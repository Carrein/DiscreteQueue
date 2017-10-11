import java.util.List;

/**
 * An abstract customer class which setups the necssary abstract methods and
 * constructor for subclasses PioneerCustomer, KiasuCustomer and
 * TypicalCustomer. Keeps track of waiting time and id.
 */
abstract class Customer{
  /**
   * The service time required by this customer.
   */
  private final double serviceTime;
  /**
   * The time this customer start waiting in a queue.  0 if this customer
   * never joins a queue.
   */
  private double timeStartedWaiting;
  /**
   * The time this customer waited in a queue.  Only valid after stopWaitingAt()
   * is called.
   */
  private double waitingTime;
  /**
   * The total number of customer created so far.
   */
  private static int numOfCustomer = 0;
  /**
   * The ID associated with this customer.
   */
  private int id;

  /**
   * Create a new customer with a given required service time.
   * 
   * @param serviceTime The time required to serve this customer.
   */
  public Customer(double serviceTime){
    this.serviceTime = serviceTime;
    this.waitingTime = 0;
    this.timeStartedWaiting = 0;
    this.id = Customer.numOfCustomer;
    Customer.numOfCustomer++;
  }

   /**
   * Record the time this customer starts waiting.
   * 
   * @param time The time this customer starts waiting.
   */
  public void startWaitingAt(double time){
    this.timeStartedWaiting = time;
  }

  /**
   * Record the time this customer stops waiting.
   * 
   * @param now The time the customer stops waiting.
   */
  public void stopWaitingAt(double now){
    //startWaitingAt must be called before this!!
    assert this.timeStartedWaiting > 0;
    this.waitingTime = now - this.timeStartedWaiting;
  }

  /**
   * Return the time this customer have waited for in the queue.
   * 
   * @return the time spent waiting.
   */
  public double getWaitingTime(){
    return this.waitingTime;
  }

  /**
   * Return the time needed to serve this customer.
   * 
   * @return the required service time.
   */
  public double getServiceTime(){
    return this.serviceTime;
  }

  /**
   * Abstract method that all subclasses must implement since all customers join
   * queues.
   * 
   * @param shop The shop being simulated (containing servers and queues).
   * @return The queue which the customer is added to.
   */
  public abstract Queue joinQueue(Shop shop);

  /**
   * Return a string representation of this customer.
   * 
   * @return The id of the customer prefixed with "C" as a stirng.
   */
  public String toString() {
    return "C" + id;
  }
}
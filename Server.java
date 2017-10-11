/**
 * Encapsulates information and logic related to a server in
 * the simulation.
 */
class Server {
  /**
   * The customer queue this server gets its next customer from.
   */
  private Queue myQueue;
  /**
   * The current customer being served.
   */
  private Customer myCustomer;
  /**
   * An internal ID for this server.
   */
  private int id;
  /**
   * The number of servers created so far.
   */
  private static int numOfServers;
  /**
   * A toggle if serve goes on break.
   */
  private boolean onBreak;

  /**
   * Create a server with a given customer queue to draw
   * customer from.
   * 
   * @param q A queue of customer
   */
  public Server(Queue q) {
    this.myQueue = q;
    this.myCustomer = null;
    this.id = Server.numOfServers;
    this.onBreak = false;
    Server.numOfServers++;
  }

  /**
   * Find a customer to serve next.  The customer is removed from
   * the customer queue and returned.  If no customer is available,
   * this server becomes idle.
   * 
   * @return The new customer to be served.
   */
  public Customer getNextCustomer() {
    Customer customer = myQueue.next();
    if (customer != null) {
      return customer;
    }
    return null;
  }

  /**
   * Serve the given customer.
   * 
   * @param customer The customer to be served.
   */
  public void serve(Customer customer) {
    this.myCustomer = customer;
  }

  /**
   * The server is done serving myCustomer.
   */
  public void done() {
    this.myCustomer = null;
  }

  /**
   * Check if the current server is idle or not. Server must not be serving a 
   * customer and on a break to be idle.
   * 
   * @return true if the server is idle, false otherwise.
   */
  public boolean isIdle() {
    return (this.myCustomer == null && !onBreak);
  }

  /**
   * Sets server status to be on break.
   */
  public void goForBreak() {
    this.onBreak = true;
  }
  
  /**
   * Sets server status to be off break.
   */
  public void backFromBreak() {
    this.onBreak = false;
  }

  /**
   * Return a string representation of the current server.
   * @return The server id prefixed with "S" in string format.
   */
  public String toString() {
    return "S" + this.id;
  }
}
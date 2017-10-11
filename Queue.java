import java.util.LinkedList;
import java.util.List;

/**
 * Queues are generate for each server to store customers FIFO style.
 */

class Queue {
  /**
   * A list collection of customers.
   */

  private final List<Customer> list;
  /**
   * An ID for this queue.
   */

  private final int id;
  /**
   * The number of queues created so far.
   */

  private static int numOfQueues = 0;
  
  /**
   * Create an empty customer queue.
   */
  public Queue() {
    this.list = new LinkedList<Customer>();
    this.id = Queue.numOfQueues;
    Queue.numOfQueues++;
  }

  /**
   * Add a customer to the end of this queue.
   * 
   * @param c The customer who joins this queue.
   * @return true if the customer joins the queue, false otherwise.
   */
  public boolean add(Customer c) {
    return this.list.add(c);
  }
  
  /**
   * Add a customer to the front of this queue.
   * 
   * @param c The customer who joins this queue.
   */
  //Pushes customer to top of the stack.
  public void bump(Customer c) {
    this.list.add(0, c);
  }

  /**
   * Remove a customer from this queue.
   * 
   * @param c The customer who is in this queue
   */
  public void remove(Customer c) {
    this.list.remove(c);
  }

  /**
   * Get size of this queue.
   * 
   * @return size of current queue.
   */
  public int size() {
    return this.list.size();
  }

  /**
   * Checks if a customer exist in this queue.
   * 
   * @param c The customer who is in this queue.
   * @return true if the customer exists, false otherwise.
   */
  public boolean contains(Customer c) {
    return this.list.contains(c);
  }

  /**
   * Gets customer's position in this queue.
   * 
   * @param c The customer who is in this queue.
   * @return index of customer's position in this queue.
   */
  public int indexOf(Customer c) {
    return this.list.indexOf(c);
  }

  /**
   * Remove the next customer from this queue.
   * @return The customer at the head of the queue, or null if
   *     the queue is empty.
   */
  public Customer next() {
    if (this.list.isEmpty()) {
      return null;
    } else {
      return this.list.remove(0);
    }
  }

  /**
   * Return a string representation of this queue.
   * @return The id of this queue with prefix "Q" in string form.
   */
  public String toString() {
    return "Q" + id;
  }
}
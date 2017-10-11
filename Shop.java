import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a shop class that put all the states of the simulation
 * in one place so that it can be passed to events.
 */
class Shop {
  /**
   * A list of servers in the simulation.
   */
  private List<Server> servers;

  /**
   * A list of queues for customers.
   */
  private List<Queue> queues;

  /**
   * Initialize the servers and queues of the simulation.
   *
   * @param  numOfServers The number of servers in the shop (c).
   * @param  numOfQueues The number of queues in the shop (must be 1 or c).
   */
  public Shop(int numOfServers, int numOfQueues) {
    servers = new LinkedList<Server>();
    queues = new LinkedList<Queue>();

    if (numOfQueues == 1) {
      Queue q = new Queue();
      queues.add(q);
      for (int i = 0; i < numOfServers; i++) {
        servers.add(new Server(q));
      }
    } else {
      for (int i = 0; i < numOfServers; i++) {
        Queue q = new Queue();
        queues.add(q);
        servers.add(new Server(q));
      }
    }
  }

  /**
   * Finds an idle server.
   *
   * @return An idle server, or null if every server is busy.
   */
  public Server findIdleServer() {
    for (Server s: servers) {
      if (s.isIdle()) {
        return s;
      }
    }
    return null;
  }

  /**
   * Return a random queue.
   * 
   * @return a randomly chosen queue from the list of queues.
   */
  public Queue getRandomQueue() {
    return queues.get(Simulator.rnd.nextInt(queues.size()));
  }

  /**
   * Returns the shortest queue.
   * 
   * @return a queue with the least number of customers.
   */
  public Queue getShortestQueue() {
    Queue shortest = queues.get(0);
    for (Queue q : queues) {
      if (q.size() < shortest.size()) {
        shortest = q;
      }
    }
    return shortest;
  }

  /**
   * Returns a customer position in any queue.
   * 
   * @param c the customer whose position we want to know.
   * @return the index in a queue of the customer.
   */
  public int getCustomerPosition(Customer c){
    //position is set to -1 as 0 is head of queue.
    int position = -1;
    for(Queue q : queues){
      if(q.contains(c)){
        position = q.indexOf(c);
      }
    }
    return position;
  }

  /**
   * Checks the current customer position relative to the shortest queue in the
   * system.
   * 
   * @param c the customer who is looking to switching queue.
   * @return if there is a shorter queue for customer to join.
   */
  public boolean changeQueue(Customer c){
    return (getCustomerPosition(c) > getShortestQueue().size());
  }

  /**
   * Remove a customer from the system.
   * 
   * @param c the customer to be removed.
   */
  public void remove(Customer c){
    for(Queue q : queues){
      q.remove(c);
    }
  }
}

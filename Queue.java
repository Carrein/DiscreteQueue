import java.util.*;

/**
 * Queues are generate for each server to store customers FIFO style.
 */

class Queue{
  private final List<Customer> list;
  private final int id;
  private static int numOfQueues = 0;

  public Queue(){
    this.list = new LinkedList<Customer>();
    this.id = Queue.numOfQueues;
    Queue.numOfQueues++;
  }

  public boolean add(Customer customer){
    return this.list.add(customer);
  }

  //Pushes customer to top of the stack.
  public void bump(Customer customer){
    this.list.add(0, customer);
  }

  public void remove(Customer customer){
    this.list.remove(customer);
  }

  public int size(){
    return this.list.size();
  }

  public boolean contains(Customer customer){
    return this.list.contains(customer);
  }

  public int indexOf(Customer customer){
    return this.list.indexOf(customer);
  }

  public Customer next(){
    if(this.list.isEmpty()){
      return null;
    }else{
      return this.list.remove(0);
    }
  }

  public String toString(){
    return "Q" + id;
  }
}
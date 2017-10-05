import java.util.*;

class Queue{
  private final List<Customer> list;
  private final int id;
  private static int numOfQueues = 0;

  public Queue(){
    this.list = new LinkedList<Customer>();
    this.id = Queue.numOfQueues;
    Queues.numOfQueues++;
  }

  public boolean add(Customer customer){
    return this.list.add(customer);
  }

  //Pushes customer to top of the stack.
  public boolean bump(Customer custoemr){
    //for pioneer generation.
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
class Server{
  private Queue myQueue;
  private Customer myCustomer;
  private int id;
  private static int numOfServers;

  public Server(Queue q){
    this.myQueue = q;
    this.myCustomer = null;
    this.id = Server.numOfServers;
    Server.numOfServers++;
  }

  public Customer getNextCustomer(){
    Customer customer = myQueue.next();
    if(customer != null){
      return customer;
    }
    return null;
  }

  public void serve(Customer customer){
    this.myCustomer = customer;
  }

  public void done(){
    this.myCustomer = null;
  }

  public boolean isIdle(){
    return (this.myCustomer == null);
  }

  public String toString() {
    return "S" + this.id;
  }
}
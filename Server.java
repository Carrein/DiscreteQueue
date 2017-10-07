class Server{
  private Queue myQueue;
  private Customer myCustomer;
  private int id;
  private static int numOfServers;

  //Constructor.
  public Server(Queue q){
    this.myQueue = q;
    this.myCustomer = null;
    this.id = Server.numOfServers;
    Server.numOfServers++;
  }
  //polls next customer from Server's own queue.
  public Customer getNextCustomer(){
    Customer customer = myQueue.next();
    if(customer != null){
      return customer;
    }
    return null;
  }
  //Serve a customer.
  public void serve(Customer customer){
    this.myCustomer = customer;
  }
  //Set server to no customers
  public void done(){
    this.myCustomer = null;
  }
  //Check if server is serving anyone.
  public boolean isIdle(){
    return (this.myCustomer == null);
  }

  public String toString() {
    return "S" + this.id;
  }
}
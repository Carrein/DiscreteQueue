class PioneerCustomer extends Customer{

  /**
   * PioneerCustomer invokes parent class Customer's constructor to setup this
   * instance of PioneerCustomer.
   */
  public PioneerCustomer(double time){
    super(time);
  } 

  /**
   * Overrided method that pushes a customer to a new queue. For 
   * PioneerCustomer, joined queue will be random, but customer will bumped to
   * top of said queue.
   * 
   * @param shop The shop being simulated (containing servers and queues).
   * @return The queue which the customer is added to.
   */
  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getRandomQueue();
    q.bump(this);
    return q;
  }

  /**
   * Return a string representation of this customer.
   * 
   * @return The id of the customer prefixed with customer type as a string.
   */
  @Override
  public String toString() {
    return String.format(super.toString() + " pioneer customer");
  }
}
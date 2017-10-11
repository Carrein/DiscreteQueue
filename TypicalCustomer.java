class TypicalCustomer extends Customer{

  /**
   * TypicalCustomer invokes parent class Customer's constructor to setup this
   * instance of TypicalCustomer.
   */
  public TypicalCustomer(double time){
    super(time);
  }

  /**
   * Overrided method that pushes a customer to a new queue. For 
   * TypicalCustomer, joined queue will be a random at current time.
   * 
   * @param shop The shop being simulated (containing servers and queues).
   * @return The queue which the customer is added to.
   */
  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getRandomQueue();
    q.add(this);
    return q;
  }

  /**
   * Return a string representation of this customer.
   * 
   * @return The id of the customer prefixed with customer type as a string.
   */
  @Override
  public String toString() {
    return String.format(super.toString() + " typical customer");
  }
}
/**
 * SwitchEvent corresponds to the event where a customer looks for a shorter
 * queue. When a SwitchEvent occur, if the customer does not find a idle server,
 * a new SwitchEvent is queued as well.
 */
class SwitchEvent extends Event {
  /**
   * The customer who is switching queues.
  */
  private final Customer customer;
  
  /**
   * The current simulation.
   */
  private final Simulator sim;
  

  /**
   * Construct a new SwitchEvent event that corresponds to a customer
   * looking for a shorter queue. 
   * 
   * @param  sim  The simulation that generates this event, this invokes the
   * parent class's constructor.
   * @param  time The time this event occurs.
   * @param  c The customer who is looking to change queues.
   */
  public SwitchEvent(Simulator sim, double time, Customer c){
    super(time);
    this.customer = c;
    this.sim = sim;
  }

  /**
   * Run the event by checking if there is an idle server to serve
   * this customer, and generate a new switch event if there are no such server.
   * 
   * @param shop The shop object containing servers and queues.
   * @return An array of events, if there is no idle servers, a new switch event
   *     is queued. If there are idle servers, the serve event is queued to
   *     serve this current customer.
   */
  @Override
  public Event[] run(Shop shop){
    //Checks if customer is not already a)out of the system, b)at head of queue.
    if(shop.getCustomerPosition(this.customer) < 0){
      return null;
    }
    Server s = shop.findIdleServer();
    if(s != null){
      shop.remove(this.customer);
      customer.stopWaitingAt(getTime());
      Event serve = this.sim.generateServe(this.customer, s);
      return new Event[] { serve };
    }else{
      if(shop.changeQueue(this.customer)){
        shop.remove(this.customer);   
        shop.getShortestQueue().add(this.customer);
      }
      Event move = sim.generateSwitch(this.customer);
      return new Event[] { move };
    }
  }

  /**
   * Return a string description of this event.
   * 
   * @return the current customer and its event.
   */
  @Override
  public String toString() {
    return super.toString() + " " + this.customer + 
    " is looking for lanes to jump!";
  }
}
/**
 * DoneEvent corresponds to the event where a server is finished serving.
 * It will generate a potential BreakEvent based on the paramters breakChance.
 * If server does not go on break, it will serve the next customer
 * automatically. DoneEvent will also recored time taken for customer to be
 * served.
 */

class DoneEvent extends Event {
  /**
   * The server completing the serve event.
   */
  private final Server server;
  
  /**
   * The customer who was just served.
  */
  private final Customer customer;
  
  /**
   * The current simulation.
   */
  private final Simulator sim;
  
  /**
   * Construct a new DoneEvent event that corresponds to a server completing
   * a serve event.
   * 
   * @param  sim  The simulation that generates this event, this invokes the
   * parent class's constructor.
   * @param  time The time this event occurs.
   * @param  c The customer who was just served
   * @param  s The server completing the serve event.
   */
  public DoneEvent(Simulator sim, double time, Customer c, Server s) {
  super(time);
  this.sim = sim;
  this.server = s;
  this.customer = c;
  }

  /**
   * Run the event by recorgint waiting time of the current customer. DoneEvent
   * will query generateBreakChance to see if server goes for break. 
   * 
   * @param shop The shop object containing servers and queues.
   * @return An array of events, if server goes for a break a rest event is
   *     queued, else serve queries next customer and a serve event is queued.
   */
  @Override
  public Event[] run(Shop shop){
    //record waiting time.
    sim.recordCustomerServed(customer.getWaitingTime());
    this.server.done();
    
    if(sim.generateBreakChance()){
      Event rest = this.sim.generateBreak(server);
      return new Event[] { rest };
    } else {
      Customer c = this.server.getNextCustomer();
      if (c != null) {
        c.stopWaitingAt(getTime());
        Event serve = sim.generateServe(c, this.server);
        return new Event[] { serve };
      }else{
        return null;
      }
    }
  }
  
  /**
   * Return a string description of this event.
   * 
   * @return the current customer and its event and which server.
   */
  @Override
  public String toString() {
    return super.toString() + " " + this.server + " finished serving "
      + this.customer;
  }
}
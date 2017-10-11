/**
 * ArrivalEvent corresponds to the event where a customer arrives.
 * When an ArrivalEvent occurs, it will generate another arrival
 * event that will occur after some time t (where t is a random
 * inter-arrival time).
 */
class ArrivalEvent extends Event {
  /**
   * The customer who is arriving.
  */
  private final Customer customer;
  
  /**
   * The current simulation.
   */
  private final Simulator sim;

  /**
   * Construct a new ArrivalEvent event that corresponds to a customer
   * arriving into the system.
   * 
   * @param  sim  The simulation that generates this event, this invokes the
   *     parent class's constructor.
   * @param  time The time this event occurs.
   * @param  c The customer who arrives.
   */
  public ArrivalEvent(Simulator sim, double time, Customer c) {
    super(time);
    this.sim = sim;
    this.customer = c;
  }

  /**
   * Run the event by checking if there is an idle server to serve
   * this customer, places the customer in a queue if none of the 
   * server is idle. Call this results in creation of a new arrival
   * event, a switch event if customer is not served immediately, and possibly 
   * a serve event.
   * 
   * @param shop The shop object containing servers and queues.
   * @return An array of events, arrival event, a switch event if customer is
   *       not served immediately and a serve event if there is an idle server.
   */
  @Override
  public Event[] run(Shop shop) {
    Event arrival = this.sim.generateArrival();
    Server s = shop.findIdleServer();
    if (s == null) {
      //no empty servers - generate arrival and switch event.
      Queue q = this.customer.joinQueue(shop);
      this.customer.startWaitingAt(getTime());
      Event move = sim.generateSwitch(this.customer);
      return new Event[] { arrival, move };
    } else {
      //empty servers - generate arrival and serve event.
      Event serve = this.sim.generateServe(this.customer, s);
      return new Event[] { arrival, serve };
    }
  }

  /**
   * Return a string description of this event.
   * 
   * @return the current customer and its event.
   */
  @Override
  public String toString() {
    return super.toString() + " " + this.customer + " arrives";
  }
}
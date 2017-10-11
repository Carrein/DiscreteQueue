/**
 * ServeEvent corresponds to the event where a customer is being served.
 * This can be called during Arrival, Done and Resume events. It will 
 * generate another arrival event that will occur after some time t 
 * (where t is a random inter-arrival time).
 */
class ServeEvent extends Event {
  /**
   * The server conducting the serve event.
   */
  private final Server server;

  /**
   * The customer who is being served.
  */
  private final Customer customer;
  
  /**
   * The current simulation.
   */
  private final Simulator sim;

  /**
   * Construct a new ServeEvent event that corresponds to a customer being
   * served by a server.
   * 
   * @param  sim  The simulation that generates this event, this invokes the
   *     parent class's constructor.
   * @param  time The time this event occurs.
   * @param  c The customer who is being served.
   * @param  s The server behind the serve event.
   */
  public ServeEvent(Simulator sim, double time, Customer c, Server s) {
    super(time);
    this.sim = sim;
    this.server = s;
    this.customer = c;
  }

  /**
   * Run this event by calling the serve method in the current server.
   * 
   * @param shop The shop object containing servers and queues.
   * @return An array of events, containing a done event.
   */
  @Override
  public Event[] run(Shop shop) {
    server.serve(customer);
    Event done = sim.generateDone(customer, this.server);
    return new Event[] { done };
  }

  /**
   * Return a string description of this event.
   * 
   * @return the current customer and its event and which server.
   */
  @Override
  public String toString() {
    return super.toString() + " " + this.server + " serves "
      + this.customer;
  }
}
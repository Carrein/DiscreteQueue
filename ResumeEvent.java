/**
 * ResumeEvent corresponds to the event where a server returns from break. 
 * The event queries for a new customer and generate a serve event if there is
 * one.
 */
class ResumeEvent extends Event {
  /**
   * The current simulation.
   */
  private final Simulator sim;
  /**
   * The server going on break.
   */
  private final Server server;

  /**
   * Construct a new BreakEvent event that corresponds to a server returning
   * from break.
   * 
   * @param  sim  The simulation that generates this event, this invokes the
   * parent class's constructor.
   * @param  time The time this event occurs.
   * @param  s The server returning from break.
   */
  public ResumeEvent(Simulator sim, double time, Server s){
    super(time);
    this.sim = sim;
    this.server = s;
  }

  /**
   * Run the event by returning the serve from break. A serve event might be
   * created.
   * 
   * @param shop The shop object containing servers and queues.
   * @return An array of events which potentially might contain a serve event if
   *     there are still customers in this server's queue.
   */
  @Override
  public Event[] run(Shop shop) {
    this.server.backFromBreak();
    Customer c = this.server.getNextCustomer();
    if(c != null){
      c.stopWaitingAt(getTime());
      Event serve = sim.generateServe(c, server);
      return new Event[] { serve };
    }else{
      return null;
    }
  }

  /**
   * Return a string description of this event.
   * 
   * @return the current server and its event.
   */
  @Override
  public String toString() {
    return super.toString() + " " + this.server + " is returning from a break.";
  }
}
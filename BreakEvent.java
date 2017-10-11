/**
 * BreakEvent corresponds to the event where a server goes on break. A resume
 * event is generated when the break is over.
 */
class BreakEvent extends Event {
  /**
   * The current simulation.
   */
  private final Simulator sim;
  /**
   * The server going on break.
   */
  private final Server server;

  /**
   * Construct a new BreakEvent event that corresponds to a server going
   * on break.
   * 
   * @param  sim  The simulation that generates this event, this invokes the
   * parent class's constructor.
   * @param  time The time this event occurs.
   * @param  s The server going on break.
   */
  public BreakEvent(Simulator sim, double time, Server s){
    super(time);
    this.sim = sim;
    this.server = s;
  }

  /**
   * Run the event by placing this server on break. A resume event will release
   * this serve after a random time has elapsed.
   * 
   * @param shop The shop object containing servers and queues.
   * @return An array of events compromising of a resume event only.
   */
  @Override
  public Event[] run(Shop shop) {
    this.server.goForBreak();
    Event resume = sim.generateResume(server);
    return new Event [] { resume };
  }

  /**
   * Return a string description of this event.
   * 
   * @return the current server and its event.
   */
  @Override
  public String toString() {
    return super.toString() + " " + this.server + " is going for a break!";
  }
}
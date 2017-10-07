class BreakEvent extends Event {
  private final Simulator sim;
  private final Server server;

  public BreakEvent(Simulator sim, double time, Server s){
    super(time);
    this.sim = sim;
    this.server = s;
  }

  @Override
  public Event[] run(Shop shop) {
    Event resume = sim.generateResume(server);
    return new Event [] { resume };
    //set a new resume event
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.server + " is going for a break!";
  }
}
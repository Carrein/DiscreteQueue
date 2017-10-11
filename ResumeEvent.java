class ResumeEvent extends Event {
  private final Simulator sim;
  private final Server server;

  public ResumeEvent(Simulator sim, double time, Server s){
    super(time);
    this.sim = sim;
    this.server = s;
  }

  @Override
  public Event[] run(Shop shop) {
    //if this event is activated, it means break time's over boys!
    this.server.backFromBreak();
    Customer c = this.server.getNextCustomer();
    if(c != null){
      c.stopWaitingAt(getTime());
      Event serveEvent = sim.generateServe(c, server);
      return new Event[] { serveEvent };
    }else{
      return null;
    }
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.server + " is returning from a break.";
  }
}
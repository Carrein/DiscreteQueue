import java.util.List;

class DoneEvent extends Event {
  private final Server server;
  private final Customer customer;
  private final Simulator sim;
  
  public DoneEvent(Simulator sim, double time, Customer c, Server s) {
  super(time);
  this.sim = sim;
  this.server = s;
  this.customer = c;
  }

  @Override
  public Event[] run(Shop shop){
    sim.recordCustomerServed(customer.getWaitingTime());
    //Record time regardless
    if(sim.generatebreakChance()){
      Event rest = this.sim.generateBreak(server);
      return new Event[] { rest };
    }else{
      this.server.done();
      Event resume = this.sim.generateResume(server);
      return new Event[] { resume };
    }
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.server + " finished serving "
      + this.customer;
  }
}
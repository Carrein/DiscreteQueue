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
    this.server.done();
    if(sim.generateBreakChance()){
      Event rest = this.sim.generateBreak(server);
      return new Event[] { rest };
    }else{
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

  @Override
  public String toString() {
    return super.toString() + " " + this.server + " finished serving "
      + this.customer;
  }
}
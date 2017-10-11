import java.util.*;

class ArrivalEvent extends Event {
  private final Customer customer;
  private final Simulator sim;

  public ArrivalEvent(Simulator sim, double time, Customer c) {
    super(time);
    this.sim = sim;
    this.customer = c;
  }

  @Override
  public Event[] run(Shop shop) {
    //Always generate a new arrival regardless!
    Event arrival = this.sim.generateArrival();    

    Server s = shop.findIdleServer();
    if(s == null){
      //no idle servers available...
      Queue q = this.customer.joinQueue(shop);
      //customer joins random queue.
      this.customer.startWaitingAt(getTime());
      //starts his clock *grumbles*
      Event move = sim.generateSwitch(this.customer);
      //customer might get fidgety, set a switch event for him too.
      //Event arrival = this.sim.generateArrival();
      //if there was no server available, return a switch and new arrival event.
      
      return new Event[] { arrival, move };
    }else{
      //oh there's an idle server!
      Event serve = this.sim.generateServe(this.customer, s);
      //great, send the customer to that server stat!
      //Event arrival = this.sim.generateArrival();     
      //if there was a sever available, return a serve and new arrival event.
      return new Event[] { arrival, serve };
    }
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.customer + " arrives";
  }
}
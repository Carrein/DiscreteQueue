class SwitchEvent extends Event {
  private final Customer customer;
  private final Simulator sim;
  
  public SwitchEvent(Simulator sim, double time, Customer c){
    super(time);
    this.customer = c;
    this.sim = sim;
  }

  @Override
  public Event[] run(Shop shop){
    Server s = shop.findIdleServer();
    if(s == null){
      //if no idle, check for shortest.
      if(shop.changeQueue(customer)){
        Queue q = shop.getShortestQueue();
        shop.removeAny(customer);
        q.add(customer);
      }else{
        return new Event[] {};
        //return an empty event since,
        //a) no move.
        //b) or no such customer.
      }
      Event move = sim.generateSwitch(this.customer);
      return new Event[] { move };
    }else{
      Event serve = this.sim.generateServe(customer, s);
      return new Event[] { serve };
    }
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.customer + " is changing queues!";
  }
}
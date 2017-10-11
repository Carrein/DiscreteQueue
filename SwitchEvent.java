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
    if(shop.getCustomerPosition(this.customer) < 0){
      return null;
    }
    Server s = shop.findIdleServer();
    if(s != null){
      shop.remove(this.customer);
      customer.stopWaitingAt(getTime());
      Event serve = this.sim.generateServe(this.customer, s);
      return new Event[] { serve };
    }else{
      if(shop.changeQueue(this.customer)){
        shop.remove(this.customer);   
        shop.getShortestQueue().add(this.customer);
      }
      Event move = sim.generateSwitch(this.customer);
      return new Event[] { move };
    }
    //Event move = sim.generateSwitch(this.customer);
    //return new Event[] { move };
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.customer + " is looking for lanes to jump!";
  }
}
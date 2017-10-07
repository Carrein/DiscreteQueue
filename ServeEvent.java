import java.util.List;

class ServeEvent extends Event {
  private final Server server;
  private final Customer customer;
  private final Simulator sim;

  public ServeEvent(Simulator sim, double time, Customer customer,
  Server server) {
    super(time);
    this.sim = sim;
    this.server = server;
    this.customer = customer;
  }

  @Override
  public Event[] run(Shop shop) {
    server.serve(customer);
    Event e = sim.generateDone(customer, this.server);
    return new Event[] { e } ;
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.server + " serves "
      + this.customer;
  }
}
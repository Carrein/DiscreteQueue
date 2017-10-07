class TypicalCustomer extends Customer{

  public TypicalCustomer(double time){
    super(time);
  }

  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getRandomQueue();
    q.add(this);
    return q;
  }

  @Override
  public String toString() {
    return String.format(super.toString() + " typical customer");
  }
}
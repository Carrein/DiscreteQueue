class PioneerCustomer extends Customer{

  public PioneerCustomer(double time){
    super(time);
  } 

  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getRandomQueue();
    q.bump(this);
    return q;
  }

  @Override
  public String toString() {
    return String.format(super.toString() + " pioneer customer");
  }
}
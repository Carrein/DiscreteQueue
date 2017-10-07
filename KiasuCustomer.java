class KiasuCustomer extends Customer{

  public KiasuCustomer(double time){
    super(time);
  }

  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getShortestQueue();
    q.add(this);
    return q;
  }

  @Override
  public String toString() {
    return String.format(super.toString() + " kiasu customer");
  }
}
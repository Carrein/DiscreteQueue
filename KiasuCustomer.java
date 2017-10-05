class KiasuCustomer extends Customer{

  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getShortestQueue();
    q.add(this);
    return q;
  }
}
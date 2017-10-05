class TypicalCustomer extends Customer{

  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getRandomQueue();
    q.add(this);
    return q;
  }
}
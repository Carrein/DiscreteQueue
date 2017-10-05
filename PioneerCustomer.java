class PioneerCustomer extends Customer{

  @Override
  public Queue joinQueue(Shop shop) {
    Queue q = shop.getRandomQueue();
    q.bump(this);
    return q;
  }
  
}
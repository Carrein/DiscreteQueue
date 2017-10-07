import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


class Shop {
  private List<Server> servers;
  private List<Queue> queues;

  public Shop(int numOfServers, int numOfQueues) {
    servers = new LinkedList<Server>();
    queues = new LinkedList<Queue>();

    if (numOfQueues == 1) {
      Queue q = new Queue();
      queues.add(q);
      for (int i = 0; i < numOfServers; i++) {
        servers.add(new Server(q));
      }
    } else {
      for (int i = 0; i < numOfServers; i++) {
        Queue q = new Queue();
        queues.add(q);
        servers.add(new Server(q));
      }
    }
  }

  public Server findIdleServer() {
    for (Server s: servers) {
      if (s.isIdle()) {
        return s;
      }
    }
    return null;
  }

  public Queue getRandomQueue() {
    return queues.get(Simulator.rnd.nextInt(queues.size()));
  }

  public Queue getShortestQueue() {
    
    int maxLength = 99999;
    Queue r  = null;
    for(Queue q : queues){
      if(maxLength > q.size()){
        maxLength = q.size();
        r = q;
      }
    }
    return r;
  }

  public int getCustomerPosition(Customer c){
    for(Queue q : queues){
      if(q.contains(c)){
        return q.indexOf(c);
        //return position
      }
    }
    return 0;
  }

  public boolean changeQueue(Customer c){
    if(getCustomerPosition(c) > getShortestQueue().size()){
      return true;
    }
    return false;
  }

  public void removeAny(Customer c){
    for(Queue q : queues){
      q.remove(c);
    }
  }
}

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
    Queue shortest  = queues.get(0);
    for(Queue q : queues){
      if(q.size() < shortest.size()){
        shortest = q;
      }
    }
    return shortest;
  }

  public int getCustomerPosition(Customer c){
    int position = -1;
    for(Queue q : queues){
      if(q.contains(c)){
        position = q.indexOf(c);
      }
    }
    return position;
  }

  public boolean changeQueue(Customer c){
    return (getCustomerPosition(c) > getShortestQueue().size());
  }

  public void remove(Customer c){
    for(Queue q : queues){
      q.remove(c);
    }
  }
}

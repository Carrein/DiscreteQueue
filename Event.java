abstract class Event implements Comparable<Event>{
  private final double time;

  public abstract Event[] run();

  public Event(double time){
    this.time = time;
  }

  public double getTime() {
    return time;
  }

  public boolean happensBefore(double time) {
    return this.time < time;
  }

  @Override
  public int compareTo(Event e) {
    return (int)Math.signum(this.time - e.time);
  }

  @Override
  public String toString() {
    return String.format("%6.3f", this.time);
  }

  public void log(String msg) {
    System.out.println(this + " " + msg);
  }

  public void log() {
    System.out.println(this);
  }
}
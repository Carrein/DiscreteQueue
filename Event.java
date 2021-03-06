/**
 * An abstract class that represents an event in the simulation.  Each event
 * has a time, representing the time at which this event occurs in the
 * simulation time line.  All concrete classes the inherit the Event class
 * must implements run(), to customize how a specific event should be
 * simulated by the simulator.
 */

abstract class Event implements Comparable<Event> {
  /**
   * The time at which this event occurs.
   */
  private final double time;

  /**
   * Abstract method that all subclasses must implement.
   * 
   * @param  shop The shop being simulated (containing servers and queues).
   * @return An array of new events that are generated by this event.
   */
  public abstract Event[] run(Shop shop);

  /**
   * Create an event to occur at a given time.
   * 
   * @param  time The time this event occurs in the simulation time line.
   */
  public Event(double time) {
    this.time = time;
  }

  /**
   * Return the time of this event.
   * 
   * @return The time this event occurs in the simulation time line.
   */
  public double getTime() {
    return time;
  }

  /**
   * Checks if this event happens before a given time.
   * 
   * @param  time The time bound to check against.
   * @return true if this event happens before the given time, false otherwise.
   */
  public boolean happensBefore(double time) {
    return this.time < time;
  }

  /**
   * Define the natural ordering of events by their time.  Earlier time
   * is ordered before the later time.
   * 
   * @param  e Another event to compare against.
   * @return 0 if two time is equal, a positive number if the event has
   *     later time then e, a negative number otherwise.
   */
  @Override
  public int compareTo(Event e) {
    return (int)Math.signum(this.time - e.time);
  }

  /**
   * Return a string representation of this event, in time form.
   * 
   * @return A formatted string of the time of this event.
   */
  @Override
  public String toString() {
    return String.format("%6.3f", this.time);
  }
  
  /**
   * A utility function to log this event to standard output.
   * 
   * @param msg A message to print along with this event.
   */
  public void log(String msg) {
    System.out.println(this + " " + msg);
  }

  /**
   * A utility function to log this event to standard output.
   */
  public void log() {
    System.out.println(this);
  }
}
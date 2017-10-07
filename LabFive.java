class LabFive {
  /**
   * The main function.  It simply creates a simulator and a simulation,
   * and ask the simulator to run the simulation.
   * @param args The command line argument.  If empty, read the simulation
   *     configuration from stdin.  Otherwise, the first argument is a
   *     filename, and the configuration parameters will be read from
   *     that file.
   */
  public static void main(String[] args) {
    Simulator sim = new Simulator(args);
    sim.run();
  }
}
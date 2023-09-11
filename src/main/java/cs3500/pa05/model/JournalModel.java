package cs3500.pa05.model;

/**
 * Represents a journal object
 */
public class JournalModel {
  private String name;
  private int maxEvents;
  private int maxTasks;
  private String password;

  /**
   * A constructor for the journal object
   *
   * @param name The name
   * @param maxEvents The max Events
   * @param maxTasks The max Tasks
   * @param password The password
   */
  public JournalModel(String name, int maxEvents, int maxTasks, String password) {
    this.name = name;
    this.maxEvents = maxEvents;
    this.maxTasks = maxTasks;
    this.password = password;
  }

  /**
   * Sets the password
   *
   * @param password the password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the password
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets the name
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the max events count
   *
   * @return the max events
   */
  public int getMaxEvents() {
    return maxEvents;
  }

  /**
   * Gets the max task count
   *
   * @return the max task count
   */
  public int getMaxTasks() {
    return maxTasks;
  }
}

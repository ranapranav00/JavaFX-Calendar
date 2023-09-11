package cs3500.pa05.model.bullets;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Time;

/**
 * Represents an event
 */
public class Event extends AbstractBullet implements EventBullet {
  Time startTime;
  int duration;

  /**
   * A constructor for an event
   *
   * @param name The name of an event
   * @param description The description for an event
   * @param day The day an event occurs on
   * @param category The category of an event
   * @param startTime The start time of an event
   * @param duration the duration of the event
   */
  public Event(String name, String description, Day day, Category category, Time startTime,
               int duration) {
    super(name, description, day, category);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * Gets the start time
   *
   * @return the start time
   */
  @Override
  public Time getStartTime() {
    return startTime;
  }

  /**
   * Sets  the start time
   *
   * @param startTime  the start time
   */
  @Override
  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  /**
   * Gets the duration
   *
   * @return the duration
   */
  @Override
  public int getDuration() {
    return duration;
  }

  /**
   * Sets the duration
   *
   * @param duration the duration
   */
  @Override
  public void setDuration(int duration) {
    this.duration = duration;
  }
}
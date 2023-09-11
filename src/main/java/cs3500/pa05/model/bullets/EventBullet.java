package cs3500.pa05.model.bullets;

import cs3500.pa05.model.Time;

/**
 * An interface containing the rest of the functionality of an event
 */
public interface EventBullet extends Bullet {
  /**
   * Gets the start time
   *
   * @return the start time
   */
  Time getStartTime();

  /**
   * Sets the start time
   *
   * @param time the start time
   */
  void setStartTime(Time time);

  /**
   * Gets the duration
   *
   * @return the duration
   */
  int getDuration();

  /**
   * Sets the duration
   *
   * @param duration the duration
   */
  void setDuration(int duration);
}

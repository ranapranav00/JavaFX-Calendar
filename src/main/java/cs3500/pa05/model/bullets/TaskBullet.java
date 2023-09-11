package cs3500.pa05.model.bullets;

/**
 * An interface with the remaining functionality of a task
 */
public interface TaskBullet extends Bullet {
  /**
   * Gets the completion status
   *
   * @return the completion status
   */
  boolean getIsComplete();

  /**
   * Sets the completion status
   *
   * @param isComplete the completion status
   */
  void setIsComplete(Boolean isComplete);
}

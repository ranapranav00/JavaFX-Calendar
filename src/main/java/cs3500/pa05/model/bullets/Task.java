package cs3500.pa05.model.bullets;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;

/**
 * A task object
 */
public class Task extends AbstractBullet implements TaskBullet {
  private boolean isComplete;

  /**
   * A constructor for a task
   *
   * @param name The name
   * @param description The description
   * @param day The day
   * @param category The category
   * @param isComplete The completion status
   */
  public Task(String name, String description, Day day, Category category, Boolean isComplete) {
    super(name, description, day, category);
    this.isComplete = isComplete;
  }

  /**
   * Gets the completion status
   *
   * @return the completion status
   */
  @Override
  public boolean getIsComplete() {
    return isComplete;
  }

  /**
   * Sets the completion status
   *
   * @param isComplete the completion status
   */
  @Override
  public void setIsComplete(Boolean isComplete) {
    this.isComplete = isComplete;

  }
}

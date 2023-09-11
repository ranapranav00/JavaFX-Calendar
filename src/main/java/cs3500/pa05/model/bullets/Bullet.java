package cs3500.pa05.model.bullets;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;

/**
 * Represents a bullet
 */
public interface Bullet {
  /**
   * Gets the name
   *
   * @return the name
   */
  String getName();

  /**
   * Sets the name
   *
   * @param name the name
   */
  void setName(String name);

  /**
   * Gets the description
   *
   * @return the description
   */
  String getDescription();

  /**
   * Sets the description
   *
   * @param description the description
   */
  void setDescription(String description);

  /**
   * Gets the day of the week
   *
   * @return the day of the week
   */
  Day getDayOfTheWeek();

  /**
   * Sets the day of the week
   *
   * @param day the day of the week
   */
  void setDayOfTheWeek(Day day);

  /**
   * Gets the category
   *
   * @return the category
   */
  Category getCategory();

  /**
   * Sets the category
   *
   * @param category the category
   */
  void setCategory(Category category);
}

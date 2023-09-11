package cs3500.pa05.model.bullets;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;

/**
 * Represents an abstract bullet
 */
public class AbstractBullet implements Bullet {
  private String name;
  private String description;
  private Day day;
  private Category category;

  /**
   * Constructor for case that all 4 fields shared amongst tasks and events were filled in
   *
   * @param name        Name of Bullet
   * @param description Description of Bullet
   * @param day         Day of Bullet
   * @param category    Category of Bullet
   */
  public AbstractBullet(String name, String description, Day day, Category category) {
    this.name = name;
    this.description = description;
    this.day = day;
    this.category = category;
  }

  /**
   * Gets the name of the bullet
   *
   * @return the name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name
   *
   * @param name the name
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the description
   *
   * @return the description
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description
   *
   * @param description the description
   */
  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the day of the week
   *
   * @return the day of the week
   */
  @Override
  public Day getDayOfTheWeek() {
    return day;
  }

  /**
   * Sets the day of the week
   *
   * @param day the day of the week
   */
  @Override
  public void setDayOfTheWeek(Day day) {
    this.day = day;
  }

  /**
   * Gets the category
   *
   * @return the category
   */
  @Override
  public Category getCategory() {
    return category;
  }

  /**
   * Sets the category
   *
   * @param category the category
   */
  @Override
  public void setCategory(Category category) {
    this.category = category;
  }
}

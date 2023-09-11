package cs3500.pa05.model;

import javafx.scene.paint.Color;

/**
 * Represents a category object
 */
public class Category {
  String name;
  Color color;

  /**
   * A constructor for the category object
   *
   * @param name The name
   * @param color The color
   */
  public Category(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  /**
   * Gets the name
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the color
   *
   * @return the color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Sets the color
   *
   * @param color the color
   */
  public void setColor(Color color) {
    this.color = color;
  }
}

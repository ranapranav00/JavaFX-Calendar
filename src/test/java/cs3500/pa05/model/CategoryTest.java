package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the Category class
 */
public class CategoryTest {

  private Category category;

  /**
   * initializes variables for testing
   */
  @BeforeEach
  void setUp() {
    category = new Category("abnend", Color.SPRINGGREEN);
  }

  /**
   * tests the getName method
   */
  @Test
  void getName() {
    assertEquals("abnend", category.getName());
  }

  /**
   * tests the getColor method
   */
  @Test
  void getColor() {
    assertEquals(Color.SPRINGGREEN, category.getColor());
  }

  /**
   * tests the setColor method
   */
  @Test
  void setColor() {
    category.setColor(Color.MOCCASIN);
    assertEquals(Color.MOCCASIN, category.getColor());
  }
}
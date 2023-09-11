package cs3500.pa05.model.bullets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the task controller
 */
public class TaskTest {

  private Category category;

  private TaskBullet task;

  /**
   * initializes variables for testing
   */
  @BeforeEach
  void setUp() {
    category = new Category("barl", Color.OLDLACE);

    task = new Task("dean", "ll", Day.MONDAY, category, false);
  }

  /**
   * tests the getIsComplete method
   */
  @Test
  void getIsComplete() {
    assertFalse(task.getIsComplete());
  }

  /**
   * tests the setIsComplete method
   */
  @Test
  void setIsComplete() {
    task.setIsComplete(true);

    assertTrue(task.getIsComplete());
  }
}
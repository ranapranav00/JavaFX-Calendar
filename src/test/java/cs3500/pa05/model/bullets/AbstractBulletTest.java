package cs3500.pa05.model.bullets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Meridian;
import cs3500.pa05.model.Time;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the abstract bullet class
 */
public class AbstractBulletTest {

  private Category category;

  private Time time;

  private Bullet eventBullet;
  private Bullet taskBullet;

  /**
   * intializes variables for testing
   */
  @BeforeEach
  void setUp() {
    category = new Category("blagb", Color.BISQUE);

    time = new Time(1, 23, Meridian.AM);

    eventBullet = new Event("rara", "bing bong", Day.FRIDAY, category, time, 900);
    taskBullet = new Task("Tuesday Tomfoolery", "gaaahhhh", Day.WEDNESDAY, category, false);
  }

  /**
   * tests the getName method
   */
  @Test
  void getName() {
    assertEquals("rara", eventBullet.getName());
    assertEquals("Tuesday Tomfoolery", taskBullet.getName());
  }

  /**
   * tests the setName method
   */
  @Test
  void setName() {
    eventBullet.setName("flagura");
    assertEquals("flagura", eventBullet.getName());

    taskBullet.setName("opposa");
    assertEquals("opposa", taskBullet.getName());
  }

  /**
   * tests the getDescription
   */
  @Test
  void getDescription() {
    assertEquals("bing bong", eventBullet.getDescription());
    assertEquals("gaaahhhh", taskBullet.getDescription());
  }

  /**
   * tests the setDescription method
   */
  @Test
  void setDescription() {
    eventBullet.setDescription("b");
    assertEquals("b", eventBullet.getDescription());

    taskBullet.setDescription("h");
    assertEquals("h", taskBullet.getDescription());
  }

  /**
   * tests the getDayOfTheWeek method
   */
  @Test
  void getDayOfTheWeek() {
    assertEquals(Day.FRIDAY, eventBullet.getDayOfTheWeek());
    assertEquals(Day.WEDNESDAY, taskBullet.getDayOfTheWeek());
  }

  /**
   * tests the setDayOfTheWeek method
   */
  @Test
  void setDayOfTheWeek() {
    eventBullet.setDayOfTheWeek(Day.THURSDAY);
    assertEquals(Day.THURSDAY, eventBullet.getDayOfTheWeek());

    taskBullet.setDayOfTheWeek(Day.SATURDAY);
    assertEquals(Day.SATURDAY, taskBullet.getDayOfTheWeek());
  }

  /**
   * tests the getCategory method
   */
  @Test
  void getCategory() {
    assertEquals(category, eventBullet.getCategory());
    assertEquals(category, taskBullet.getCategory());
  }

  /**
   * tests the setCategory method
   */
  @Test
  void setCategory() {
    Category newCategory = new Category("b", Color.MEDIUMPURPLE);

    eventBullet.setCategory(newCategory);
    assertEquals(newCategory, eventBullet.getCategory());

    taskBullet.setCategory(newCategory);
    assertEquals(newCategory, taskBullet.getCategory());
  }
}
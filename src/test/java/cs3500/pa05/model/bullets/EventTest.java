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
 * represents tests for the event class
 */
public class EventTest {

  private Category category;

  private Time time;

  private EventBullet event;

  /**
   * initializes variables for testing
   */
  @BeforeEach
  void setUp() {
    category = new Category("h", Color.SIENNA);

    time = new Time(1, 1, Meridian.AM);

    event = new Event("john", "jonathan", Day.THURSDAY, category, time, 20);
  }

  /**
   * tests the getStartTime method
   */
  @Test
  void getStartTime() {
    assertEquals(time, event.getStartTime());
  }

  /**
   * tests the setStartTime method
   */
  @Test
  void setStartTime() {
    Time newTime = new Time(2, 2, Meridian.PM);
    event.setStartTime(newTime);

    assertEquals(newTime, event.getStartTime());
  }

  /**
   * tests the getDuration method
   */
  @Test
  void getDuration() {
    assertEquals(20, event.getDuration());
  }

  /**
   * tests the setDuration method
   */
  @Test
  void setDuration() {
    event.setDuration(39);

    assertEquals(39, event.getDuration());
  }
}
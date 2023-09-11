package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents a test class for the time object
 */
public class TimeTest {

  private Time time;

  /**
   * Sets up some variables before each test
   */
  @BeforeEach
  void setUp() {
    time = new Time(3, 45, Meridian.PM);
  }

  /**
   * Tests the getHour method
   */
  @Test
  void getHour() {
    assertEquals(3, time.getHour());
  }

  /**
   * Tests the getMinute method
   */
  @Test
  void getMinute() {
    assertEquals(45, time.getMinute());
  }

  /**
   * Tests the getMeridian method
   */
  @Test
  void getMeridian() {
    assertEquals(Meridian.PM, time.getMeridian());
  }
}
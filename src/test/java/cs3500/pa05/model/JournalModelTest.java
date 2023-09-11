package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents a test class for the journalModel
 */
public class JournalModelTest {

  private JournalModel journalModel;

  /**
   * Sets up variables before each test
   */
  @BeforeEach
  void setUp() {
    journalModel = new JournalModel("journal", 1, 2, "password");
  }

  /**
   * Tests the getName method
   */
  @Test
  void getName() {
    assertEquals("journal", journalModel.getName());
  }

  /**
   * Tests the getMaxEvents method
   */
  @Test
  void getMaxEvents() {
    assertEquals(1, journalModel.getMaxEvents());
  }

  /**
   * Tests the getMaxTasks method
   */
  @Test
  void getMaxTasks() {
    assertEquals(2, journalModel.getMaxTasks());
  }
}
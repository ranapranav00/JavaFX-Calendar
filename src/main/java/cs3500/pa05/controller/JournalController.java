package cs3500.pa05.controller;

import javafx.scene.control.Button;

/**
 * represents a journal controller
 */
public interface JournalController extends MainController {
  /**
   * gets the new week button from this journal controller
   *
   * @return the new week button
   */
  Button getNewWeek();

  /**
   * gets the open button from this journal controller
   *
   * @return the open button
   */
  Button getOpen();
}

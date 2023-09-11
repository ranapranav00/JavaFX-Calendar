package cs3500.pa05.controller;

import javafx.scene.control.Button;

/**
 * represents a controller for creating a new week
 */
public interface NewWeekController extends MainController {
  /**
   * gets the create button from this new week controller
   *
   * @return the create button
   */
  Button getCreateButton();

  /**
   * gets the name of the week
   *
   * @return the name of the week
   */
  String getName();

  /**
   * gets the maximum number of events per day
   *
   * @return the max number of events per day
   */
  int getMaxEvents();

  /**
   * gets the maximum number of tasks per day
   *
   * @return the max number of tasks per day
   */
  int getMaxTasks();
}

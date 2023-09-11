package cs3500.pa05.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Represents an implementation for a New Week Controller
 */
public class NewWeekControllerImpl implements NewWeekController {

  @FXML
  private TextField name;
  @FXML
  private TextField maxEvents;
  @FXML
  private TextField maxTasks;
  @FXML
  private Button create;

  /**
   * Runs the controller
   */
  public void run() {
    init();
  }

  /**
   * Initializes the data
   */
  private void init() {
    maxEvents.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        maxEvents.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
    maxTasks.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        maxTasks.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
  }

  /**
   * Returns the create button
   *
   * @return the create button
   */
  public Button getCreateButton() {
    return create;
  }

  /**
   * Gets the name of the new week
   *
   * @return the name of the new week
   */
  public String getName() {
    return name.getText();
  }

  /**
   * Gets the max events of the new week
   *
   * @return the max events of the new week
   */
  public int getMaxEvents() {
    return Integer.parseInt(maxEvents.getText());
  }

  /**
   * Gets the max tasks of the new week
   *
   * @return the max tasks of the new week
   */
  public int getMaxTasks() {
    return Integer.parseInt(maxTasks.getText());
  }
}

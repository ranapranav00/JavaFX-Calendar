package cs3500.pa05.controller;

import cs3500.pa05.model.JournalModel;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Represents a controller for setting a password
 */
public class SetPasswordController implements MainController {
  @FXML
  private HBox enterExistingPassword;
  @FXML
  private HBox createNewPassword;
  @FXML
  private TextField verifyPassword;
  @FXML
  private TextField newPassword;
  @FXML
  private Button create;

  private JournalModel journalModel;

  /**
   * A controller to set the password
   *
   * @param journalModel A journal object
   */
  public SetPasswordController(JournalModel journalModel) {
    this.journalModel = journalModel;
  }

  public HBox getCreateNewPassword() {
    return createNewPassword;
  }

  /**
   * Runs the controller
   */
  @Override
  public void run() {
    init();
  }


  /**
   * Initializes the data
   */
  private void init() {
    if (journalModel.getPassword().isEmpty() || journalModel.getPassword() == null) {
      enterExistingPassword.setVisible(false);
    } else {
      newPassword.setDisable(true);
      verifyPassword.setBackground(new Background(
          new BackgroundFill(Color.ORANGERED.brighter(), CornerRadii.EMPTY, Insets.EMPTY)));
      createNewPassword.setVisible(false);
      create.setDisable(true);

      verifyPassword.textProperty().addListener((observable, oldPass, newpAss) -> {
        if (verifyPassword.getText().equals(journalModel.getPassword())) {
          verifyPassword.setBackground(new Background(
              new BackgroundFill(Color.LAWNGREEN.brighter(), CornerRadii.EMPTY, Insets.EMPTY)));
          newPassword.setDisable(false);
          createNewPassword.setVisible(true);
          create.setDisable(false);
        } else {
          verifyPassword.setBackground(new Background(
              new BackgroundFill(Color.ORANGERED.brighter(), CornerRadii.EMPTY, Insets.EMPTY)));
          createNewPassword.setVisible(false);
          newPassword.setDisable(true);
          create.setDisable(true);
        }
      });
    }
  }

  /**
   * Gets the password
   *
   * @return the password
   */
  public String getPassword() {
    String password = newPassword.getText();
    journalModel.setPassword(password);
    return password;
  }

  /**
   * Gets the create button
   *
   * @return the create button
   */
  public Button getCreateButton() {
    return this.create;
  }
}

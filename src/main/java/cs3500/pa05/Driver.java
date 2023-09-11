package cs3500.pa05;

import cs3500.pa05.controller.CreationController;
import cs3500.pa05.controller.MainController;
import cs3500.pa05.view.JournalView;
import cs3500.pa05.view.JournalViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The driver object
 */
public class Driver extends Application {
  /**
   * The entrypoint of the program
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    Application.launch();
  }

  /**
   * Starts the program
   *
   * @param primaryStage the primary stage for this application, onto which
   *                     the application scene can be set.
   *                     Applications may create other stages, if needed, but they will not be
   *                     primary stages.
   */
  @Override
  public void start(Stage primaryStage) {
    Stage welcomeStage = new Stage();
    MainController welcomeScreenController = new CreationController(welcomeStage);
    JournalView journalView = new JournalViewImpl(welcomeScreenController, "WelcomeScreen.fxml");

    try {
      welcomeStage.setScene(journalView.load());
      welcomeScreenController.run();
      welcomeStage.show();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
  }
}

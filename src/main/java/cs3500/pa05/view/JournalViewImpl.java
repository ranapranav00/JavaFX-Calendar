package cs3500.pa05.view;

import cs3500.pa05.controller.MainController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents a journal View implementation
 */
public class JournalViewImpl implements JournalView {
  private FXMLLoader loader;

  /**
   * A constructor for the journal view impl
   *
   * @param controller a controller
   * @param fxml a string representing an fxml file
   */
  public JournalViewImpl(MainController controller, String fxml) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource(fxml));
    this.loader.setController(controller);
  }

  /**
   * Loads the scene
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load file.");
    }
  }
}

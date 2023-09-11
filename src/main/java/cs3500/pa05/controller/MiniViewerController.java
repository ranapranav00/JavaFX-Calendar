package cs3500.pa05.controller;

import cs3500.pa05.model.Category;
import java.util.List;
import javafx.scene.control.Button;

/**
 * represents a controller for a mini viewer
 */
public interface MiniViewerController extends BulletController {
  /**
   * gets the delete button from this mini viewer controller
   *
   * @return the delete button
   */
  Button getDeleteButton();

  /**
   * sets the choices in a dropdown menu for categories
   *
   * @param allCategories the list of all previously created categories
   */
  void setCategoryDropdown(List<Category> allCategories);
}

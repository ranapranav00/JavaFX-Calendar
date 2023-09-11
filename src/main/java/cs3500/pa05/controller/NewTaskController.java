package cs3500.pa05.controller;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.bullets.Task;
import java.util.List;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * represents a controller for creating a new task
 */
public class NewTaskController implements BulletController {

  private int maxTasksPerDay;
  private Map<Day, Integer> tasksInDayMap;

  @FXML
  private TextField name;
  @FXML
  private TextField description;
  @FXML
  private ChoiceBox<String> day;
  @FXML
  private TextField category;
  @FXML
  private Button create;
  //Category
  @FXML
  private HBox selectCategory;
  @FXML
  private TextField newCategory;
  @FXML
  private ChoiceBox<String> categoryDropdown;
  @FXML
  private ColorPicker color;
  private List<Category> allCategories;

  /**
   * Represents a controller for a new task
   *
   * @param maxTasksPerDay The max tasks per day
   * @param tasksInDayMap The number of tasks in a day represented in a map
   */
  public NewTaskController(int maxTasksPerDay, Map<Day, Integer> tasksInDayMap) {
    this.maxTasksPerDay = maxTasksPerDay;
    this.tasksInDayMap = tasksInDayMap;
  }

  /**
   * Sets the cateogry dropdown to the given values
   *
   * @param allCategories All categories which have not reached their max task count
   */
  public void setCategoryDropdown(List<Category> allCategories) {
    this.allCategories = allCategories;
  }

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
    for (Day d : Day.values()) {
      if (tasksInDayMap.get(d) < maxTasksPerDay) {
        day.getItems().add(d.toString());
      }
    }

    if (allCategories.size() < 1) {
      selectCategory.setVisible(false);
    } else {
      selectCategory.setVisible(true);
      categoryDropdown.getItems().add(null);
      for (Category c : allCategories) {
        categoryDropdown.getItems().add(c.getName());
      }
    }
  }

  /**
   * Gets the bullet
   *
   * @return the bullet
   */
  public Task getBullet() {
    if (!(newCategory.getText().isEmpty())) {
      return new Task(name.getText(), description.getText(),
          Day.valueOf(day.getValue().toUpperCase()),
          new Category(newCategory.getText(), color.getValue()), false);
    } else if (newCategory.getText().isEmpty() && categoryDropdown.getValue() != null) {
      if (!(categoryDropdown.getValue().isEmpty())) {
        Category selectedCategory = null;
        for (Category c : allCategories) {
          if (c.getName().equals(categoryDropdown.getValue())) {
            selectedCategory = c;
          }
        }
        return new Task(name.getText(), description.getText(),
            Day.valueOf(day.getValue().toUpperCase()),
            selectedCategory, false);
      }
    }
    return new Task(name.getText(), description.getText(),
        Day.valueOf(day.getValue().toUpperCase()),
        new Category("DEFAULT", Color.WHITE), false);
  }

  /**
   * Retrieves the create button
   *
   * @return the create button
   */
  @Override
  public Button getCreateButton() {
    return create;
  }

  /**
   * Disables the create button until fields are entered appropriately
   */
  @Override
  public void disable() {
    create.disableProperty().bind(Bindings.isEmpty(name.textProperty())
        .or(Bindings.isNull(day.valueProperty())));

  }
}

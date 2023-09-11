package cs3500.pa05.controller;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.bullets.Bullet;
import cs3500.pa05.model.bullets.Task;
import cs3500.pa05.model.bullets.TaskBullet;
import java.util.List;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * represents a controller for a task mini viewer
 */
public class MiniViewerTaskController implements MiniViewerController {

  private final TaskBullet taskBullet;
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
  private CheckBox completed;
  @FXML
  private Button save;
  @FXML
  private Button delete;

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
  private Category selectedCategory;

  /**
   * Represents a constructor for the miniviewertask controller
   *
   * @param taskBullet     A task bullet
   * @param maxTasksPerDay The max tasks per day
   * @param tasksInDayMap  The amount of tasks per day in a map
   */
  public MiniViewerTaskController(TaskBullet taskBullet, int maxTasksPerDay,
                                  Map<Day, Integer> tasksInDayMap) {
    this.taskBullet = taskBullet;
    this.maxTasksPerDay = maxTasksPerDay;
    this.tasksInDayMap = tasksInDayMap;
  }

  /**
   * Runs the controller
   */
  public void run() {
    init();
    name.setText(taskBullet.getName());
    description.setText(taskBullet.getDescription());
    day.setValue(taskBullet.getDayOfTheWeek().toString());
    completed.setSelected(taskBullet.getIsComplete());
    newCategory.setText(taskBullet.getCategory().getName());
    color.setValue(taskBullet.getCategory().getColor());
  }

  /**
   * Sets the category dropdown to the list of available categories
   *
   * @param allCategories the list of all previously created categories
   */
  @Override
  public void setCategoryDropdown(List<Category> allCategories) {
    this.allCategories = allCategories;
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
  public Bullet getBullet() {
    if (categoryDropdown.getValue() != null) {
      if (!(categoryDropdown.getValue().isEmpty())) {
        for (Category c : allCategories) {
          if (c.getName().equals(categoryDropdown.getValue())) {
            selectedCategory = c;
          }
        }
        saveState();
        newCategory.setText(selectedCategory.getName());
        color.setValue(selectedCategory.getColor());
        return new Task(name.getText(), description.getText(),
            Day.valueOf(day.getValue().toUpperCase()),
            selectedCategory, taskBullet.getIsComplete());
      }
    } else if (!(newCategory.getText().isEmpty())) {
      selectedCategory = new Category(newCategory.getText(), color.getValue());
      saveState();
      return new Task(name.getText(), description.getText(),
          Day.valueOf(day.getValue().toUpperCase()), selectedCategory,
          taskBullet.getIsComplete());
    }
    saveState();
    return new Task(name.getText(), description.getText(),
        Day.valueOf(day.getValue().toUpperCase()),
        new Category("", Color.WHITE),
        taskBullet.getIsComplete());
  }

  /**
   * Gets the create button
   *
   * @return the create button
   */
  public Button getCreateButton() {
    return save;
  }

  /**
   * Gets the delete button
   *
   * @return the delete button
   */
  public Button getDeleteButton() {
    return delete;
  }

  /**
   * Disables the save button until fields are filled appropriately
   */
  public void disable() {
    save.disableProperty().bind(Bindings.isEmpty(name.textProperty())
        .or(Bindings.isNull(day.valueProperty())));
  }

  /**
   * Saves the state of the MiniViewer for a task
   */
  private void saveState() {
    taskBullet.setName(name.getText());
    taskBullet.setDescription(description.getText());
    taskBullet.setDayOfTheWeek(Day.valueOf(day.getValue().toUpperCase()));
    //taskBullet.setCategory(category.getText());
    taskBullet.setIsComplete(completed.isSelected());
  }
}

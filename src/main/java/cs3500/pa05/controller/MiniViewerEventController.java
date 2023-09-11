package cs3500.pa05.controller;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Meridian;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.bullets.Bullet;
import cs3500.pa05.model.bullets.Event;
import cs3500.pa05.model.bullets.EventBullet;
import java.util.List;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * represents a controller for an event mini viewer
 */
public class MiniViewerEventController implements MiniViewerController {

  private final EventBullet eventBullet;
  private int maxEventsPerDay;
  private Map<Day, Integer> eventsInDayMap;

  @FXML
  private TextField name;
  @FXML
  private TextField description;
  @FXML
  private ChoiceBox<String> day;

  @FXML
  private ChoiceBox<String> hour;
  @FXML
  private ChoiceBox<String> minute;
  @FXML
  private ChoiceBox<String> meridian;
  @FXML
  private TextField duration;
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
   * A constructor for the MiniViewerEventController
   *
   * @param eventBullet An event bullet
   * @param maxEventsPerDay The maximum events per day
   * @param eventsInDayMap The number of events on each day in a map
   */
  public MiniViewerEventController(EventBullet eventBullet, int maxEventsPerDay,
                                   Map<Day, Integer> eventsInDayMap) {
    this.eventBullet = eventBullet;
    this.maxEventsPerDay = maxEventsPerDay;
    this.eventsInDayMap = eventsInDayMap;
  }

  /**
   * Runs the controller
   */
  public void run() {
    init();
    name.setText(eventBullet.getName());
    description.setText(eventBullet.getDescription());
    day.setValue(eventBullet.getDayOfTheWeek().toString());

    hour.setValue(String.valueOf(eventBullet.getStartTime().getHour()));
    minute.setValue(String.valueOf(eventBullet.getStartTime().getMinute()));
    meridian.setValue(eventBullet.getStartTime().getMeridian().toString());
    duration.setText(String.valueOf(eventBullet.getDuration()));

    newCategory.setText(eventBullet.getCategory().getName());
    color.setValue(eventBullet.getCategory().getColor());
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
   * Initializes data
   */
  private void init() {
    for (Day d : Day.values()) {
      if (eventsInDayMap.get(d) < maxEventsPerDay) {
        day.getItems().add(d.toString());
      }
    }

    meridian.getItems().addAll("AM", "PM");
    for (int i = 1; i <= 12; i++) {
      hour.getItems().add(String.valueOf(i));
    }
    for (int i = 0; i < 60; i++) {
      if (i < 10) {
        minute.getItems().add("0" + i);
      } else {
        minute.getItems().add(String.valueOf(i));
      }
    }

    duration.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        duration.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });

    if (allCategories.size() < 1) {
      selectCategory.setVisible(false);
    } else {
      selectCategory.setVisible(true);
      categoryDropdown.getItems().add(null);
      for (Category c : allCategories) {
        categoryDropdown.getItems().add(c.getName());
      }
    }

    categoryDropdown.getSelectionModel().selectedItemProperty().addListener(
        (ObservableValue<? extends String> observable, String oldCat, String newCat)
            -> newCategory.clear());
  }

  /**
   * Gets the bullet
   *
   * @return The bullet
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
        return new Event(name.getText(), description.getText(),
            Day.valueOf(day.getValue().toUpperCase()),
            selectedCategory, parseTime(), Integer.parseInt(duration.getText()));
      }
    } else if (!(newCategory.getText().isEmpty())) {
      selectedCategory = new Category(newCategory.getText(), color.getValue());
      saveState();
      return new Event(name.getText(), description.getText(),
          Day.valueOf(day.getValue().toUpperCase()), selectedCategory,
          parseTime(), Integer.parseInt(duration.getText()));
    }
    saveState();
    return new Event(name.getText(), description.getText(),
        Day.valueOf(day.getValue().toUpperCase()),
        new Category("", Color.WHITE),
        parseTime(), Integer.parseInt(duration.getText()));
  }

  /**
   * Retrieves the create button
   *
   * @return the create button
   */
  public Button getCreateButton() {
    return save;
  }

  /**
   * Retrieves the delete button
   *
   * @return the delete button
   */
  public Button getDeleteButton() {
    return delete;
  }

  /**
   * Disables a button so that it is only clickable when necessary
   */
  public void disable() {
    save.disableProperty().bind(Bindings.isEmpty(name.textProperty())
        .or(Bindings.isNull(day.valueProperty()).or(Bindings.isNull(hour.valueProperty())
            .or(Bindings.isNull(minute.valueProperty())
                .or(Bindings.isNull(meridian.valueProperty()))
                .or(Bindings.isEmpty(duration.textProperty())))))
        .or((Bindings.isNotEmpty(newCategory.textProperty())
            .and(Bindings.isNotNull(categoryDropdown.valueProperty())))));
  }

  /**
   * Parses the time into a time object
   *
   * @return the time in object form
   */
  private Time parseTime() {
    int timeHour = Integer.parseInt(hour.getValue());
    int timeMin = Integer.parseInt(minute.getValue());
    return new Time(timeHour, timeMin, Meridian.valueOf(meridian.getValue()));
  }

  /**
   * Saves the state of the miniviewer
   */
  private void saveState() {
    eventBullet.setName(name.getText());
    eventBullet.setDescription(description.getText());
    eventBullet.setDayOfTheWeek(Day.valueOf(day.getValue().toUpperCase()));
    eventBullet.setCategory(selectedCategory);
    eventBullet.setStartTime(parseTime());
    eventBullet.setDuration(Integer.parseInt(duration.getText()));
  }
}

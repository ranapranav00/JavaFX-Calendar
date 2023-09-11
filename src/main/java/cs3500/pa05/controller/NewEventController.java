package cs3500.pa05.controller;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Meridian;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.bullets.Bullet;
import cs3500.pa05.model.bullets.Event;
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
 * represents a controller for creating a new event
 */
public class NewEventController implements BulletController {

  private int maxEventsPerDay;
  private Map<Day, Integer> eventsInDayMap;
  private List<Category> allCategories;

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

  /**
   * Represents a controller for a New Event
   *
   * @param maxEventsPerDay the max events per day
   * @param eventsInDayMap the number of events per day in a map
   */
  public NewEventController(int maxEventsPerDay, Map<Day, Integer> eventsInDayMap) {
    this.maxEventsPerDay = maxEventsPerDay;
    this.eventsInDayMap = eventsInDayMap;
  }

  /**
   * Runs the controller
   */
  public void run() {
    init();
  }

  /**
   * Sets the category dropdown to the given values
   *
   * @param allCategories All categories which have not reached their max event count
   */
  public void setCategoryDropdown(List<Category> allCategories) {
    this.allCategories = allCategories;
  }

  /**
   * Initializes the data
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
  }

  /**
   * gets the bullet created by this new event controller
   *
   * @return the created bullet
   */
  public Bullet getBullet() {
    if (!(newCategory.getText().isEmpty())) {
      return new Event(name.getText(), description.getText(),
          Day.valueOf(day.getValue().toUpperCase()),
          new Category(newCategory.getText(), color.getValue()),
          parseTime(), Integer.parseInt(duration.getText()));
    } else if (newCategory.getText().isEmpty() && categoryDropdown.getValue() != null) {
      if (!(categoryDropdown.getValue().isEmpty())) {
        Category selectedCategory = null;
        for (Category c : allCategories) {
          if (c.getName().equals(categoryDropdown.getValue())) {
            selectedCategory = c;
          }
        }
        return new Event(name.getText(), description.getText(),
            Day.valueOf(day.getValue().toUpperCase()),
            selectedCategory, parseTime(), Integer.parseInt(duration.getText()));
      }
    }
    return new Event(name.getText(), description.getText(),
        Day.valueOf(day.getValue().toUpperCase()),
        new Category("DEFAULT", Color.WHITE),
        parseTime(), Integer.parseInt(duration.getText()));
  }

  /**
   * Retrieves the create button
   *
   * @return returns the create button
   */
  public Button getCreateButton() {
    return create;
  }

  /**
   * Disables the create button until fields are entered appropriately
   */
  public void disable() {
    create.disableProperty().bind(Bindings.isEmpty(name.textProperty())
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
   * @return the time object
   */
  private Time parseTime() {
    int timeHour = Integer.parseInt(hour.getValue());
    int timeMin = Integer.parseInt(minute.getValue());
    return new Time(timeHour, timeMin, Meridian.valueOf(meridian.getValue()));
  }
}
package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.bullets.Bullet;
import cs3500.pa05.model.bullets.Event;
import cs3500.pa05.model.bullets.Task;
import cs3500.pa05.model.json.CategoryJson;
import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import cs3500.pa05.model.json.TimeJson;
import cs3500.pa05.model.json.WeekJson;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * represents a controller for loading a pre-existing week
 */
public class WeekLoaderController {

  private File bujoFile;
  private ObjectMapper mapper;

  /**
   * A constructor for the weekloader controller
   *
   * @param bujoFile A bujo file to load
   */
  public WeekLoaderController(File bujoFile) {
    this.bujoFile = bujoFile;
    this.mapper = new ObjectMapper();
  }

  /**
   * Populates the map of events
   *
   * @return the map of events
   */
  public Map<Bullet, Button> populateEventMap() {
    Map<Bullet, Button> eventMap = new HashMap<>();
    addEventEntries(bujoToJson().sunday().events(), eventMap);
    addEventEntries(bujoToJson().monday().events(), eventMap);
    addEventEntries(bujoToJson().tuesday().events(), eventMap);
    addEventEntries(bujoToJson().wednesday().events(), eventMap);
    addEventEntries(bujoToJson().thursday().events(), eventMap);
    addEventEntries(bujoToJson().friday().events(), eventMap);
    addEventEntries(bujoToJson().saturday().events(), eventMap);
    return eventMap;
  }

  /**
   * Populates the map of tasks
   *
   * @return the map of tasks
   */
  public Map<Bullet, Button> populateTaskMap() {
    Map<Bullet, Button> taskMap = new HashMap<>();

    addTaskEntries(bujoToJson().sunday().tasks(), taskMap);
    addTaskEntries(bujoToJson().monday().tasks(), taskMap);
    addTaskEntries(bujoToJson().tuesday().tasks(), taskMap);
    addTaskEntries(bujoToJson().wednesday().tasks(), taskMap);
    addTaskEntries(bujoToJson().thursday().tasks(), taskMap);
    addTaskEntries(bujoToJson().friday().tasks(), taskMap);
    addTaskEntries(bujoToJson().saturday().tasks(), taskMap);

    return taskMap;
  }

  /**
   * Populates all categories
   *
   * @return a list of all categories
   */
  public List<Category> populateAllCategories() {
    List<Category> categories = new ArrayList<>();

    for (CategoryJson c : bujoToJson().categories()) {
      categories.add(adaptJsonToCategory(c));
    }

    return categories;
  }

  /**
   * Adapts a bujo file to a weekJson record
   *
   * @return the weekJson record
   */
  private WeekJson bujoToJson() {
    try {
      return mapper.getFactory().createParser(bujoFile).readValueAs(WeekJson.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Adds entries to the map of events and buttons by adapting eventJsons
   *
   * @param eventJsons A list of eventJsons
   * @param map        The map of events to buttons
   */
  private void addEventEntries(List<EventJson> eventJsons, Map<Bullet, Button> map) {
    for (EventJson e : eventJsons) {
      map.put(
          new Event(e.name(), e.description(), e.day(), adaptJsonToCategory(e.category()), //CHANGE
              adaptJsonToTime(e.startTime()), e.duration()),
          new Button(e.name()));
    }
  }

  /**
   * Adapts a timeJson to a normal time object
   *
   * @param timeJson A timeJson record
   * @return A time object
   */
  private Time adaptJsonToTime(TimeJson timeJson) {
    return new Time(timeJson.hour(), timeJson.minute(), timeJson.meridian());
  }

  /**
   * Adds entries to the map of events and buttons by adapting taskJsons
   *
   * @param taskJsons A list of task jsons
   * @param taskMap   A map of tasks and buttons
   */
  public void addTaskEntries(List<TaskJson> taskJsons, Map<Bullet, Button> taskMap) {
    for (TaskJson t : taskJsons) {
      taskMap.put(new Task(t.name(), t.description(), t.day(), adaptJsonToCategory(t.category()),
              t.isComplete()), //CHANGE
          new Button(t.name()));
    }
  }

  /**
   * Adapts a categoryJson to a category object
   *
   * @param c a categoryJson
   * @return the adapted category
   */
  private Category adaptJsonToCategory(CategoryJson c) {
    return new Category(c.name(), Color.color(c.redValue(), c.greenValue(), c.blueValue()));
  }

  /**
   * Gets the name from the bujo
   *
   * @return the name
   */
  public String getName() {
    return bujoToJson().name();
  }

  /**
   * Gets the max events from the bujo
   *
   * @return the max events
   */
  public int getMaxEvents() {
    return bujoToJson().maxEvents();
  }

  /**
   * Gets the max tasks from the bujo
   *
   * @return the max tasks
   */
  public int getMaxTasks() {
    return bujoToJson().maxTasks();
  }

  /**
   * Gets the startsWith day from the bujo
   *
   * @return the starts with day
   */
  public Day getStartsWith() {
    return bujoToJson().startsWith();
  }

  /**
   * Gets the password from the bujo
   *
   * @return the password
   */
  public String getPassword() {
    return bujoToJson().password();
  }

}

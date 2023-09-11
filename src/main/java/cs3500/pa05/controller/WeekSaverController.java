package cs3500.pa05.controller;

import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.bullets.Bullet;
import cs3500.pa05.model.bullets.EventBullet;
import cs3500.pa05.model.bullets.TaskBullet;
import cs3500.pa05.model.json.CategoryJson;
import cs3500.pa05.model.json.DayJson;
import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import cs3500.pa05.model.json.TimeJson;
import cs3500.pa05.model.json.WeekJson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * represents a controller for saving the current week
 */
public class WeekSaverController {

  private String weekName;
  private int maxEvents;
  private int maxTasks;
  private Day startsWith;
  private List<Category> categories;
  private String password;
  private Map<Bullet, Button> eventMap;
  private Map<Bullet, Button> taskMap;
  private VBox sundayBullets;
  private VBox mondayBullets;
  private VBox tuesdayBullets;
  private VBox wednesdayBullets;
  private VBox thursdayBullets;
  private VBox fridayBullets;
  private VBox saturdayBullets;

  /**
   * A constructor for the weekSaver controller
   *
   * @param journal    the journal
   * @param startsWith the day it starts with
   * @param categories the categories list
   * @param weekvboxes the days of the week in vboxes
   * @param eventMap   the map of events to buttons
   * @param taskMap    the map of tasks to buttons
   */
  public WeekSaverController(JournalModel journal, Day startsWith, List<Category> categories,
                             ArrayList<VBox> weekvboxes, Map<Bullet, Button> eventMap,
                             Map<Bullet, Button> taskMap) {
    this.weekName = journal.getName();
    this.maxEvents = journal.getMaxEvents();
    this.maxTasks = journal.getMaxTasks();
    this.startsWith = startsWith;
    this.categories = categories;
    //this.password = journal.getPassword();
    this.eventMap = eventMap;
    this.taskMap = taskMap;
    sundayBullets = weekvboxes.get(0);
    mondayBullets = weekvboxes.get(1);
    tuesdayBullets = weekvboxes.get(2);
    wednesdayBullets = weekvboxes.get(3);
    thursdayBullets = weekvboxes.get(4);
    fridayBullets = weekvboxes.get(5);
    saturdayBullets = weekvboxes.get(6);
  }

  /**
   * Converts a week into a weekJson
   *
   * @return a weekJson
   */
  public WeekJson weekToJson() {
    DayJson sundayJson = dayToJson(getEventBullets(sundayBullets), getTaskBullets(sundayBullets));
    DayJson mondayJson = dayToJson(getEventBullets(mondayBullets), getTaskBullets(mondayBullets));
    DayJson tuesdayJson =
        dayToJson(getEventBullets(tuesdayBullets), getTaskBullets(tuesdayBullets));
    DayJson wednesdayJson =
        dayToJson(getEventBullets(wednesdayBullets), getTaskBullets(wednesdayBullets));
    DayJson thursdayJson =
        dayToJson(getEventBullets(thursdayBullets), getTaskBullets(thursdayBullets));
    DayJson fridayJson = dayToJson(getEventBullets(fridayBullets), getTaskBullets(fridayBullets));
    DayJson saturdayJson =
        dayToJson(getEventBullets(saturdayBullets), getTaskBullets(saturdayBullets));

    return new WeekJson(weekName, maxEvents, maxTasks, startsWith, allCategoriesToJson(), password,
        sundayJson, mondayJson, tuesdayJson, wednesdayJson, thursdayJson, fridayJson, saturdayJson);
  }

  /**
   * Gets eventBullets from the given vbox day
   *
   * @param bullets A vbox of bullets for a given day
   * @return The eventBullets from the vbox
   */
  private ArrayList<EventBullet> getEventBullets(VBox bullets) {
    ArrayList<EventBullet> events = new ArrayList<>();
    for (Node n : bullets.getChildren()) {
      for (Map.Entry<Bullet, Button> entries : eventMap.entrySet()) {
        if (entries.getValue().equals(n)) {
          Bullet b = entries.getKey();
          events.add((EventBullet) b);
        }
      }
    }
    return events;
  }

  /**
   * Gets taskBullets from the given vbox day
   *
   * @param bullets A vbox of bullets for a given day
   * @return The taskBullets from the vbox
   */
  private ArrayList<TaskBullet> getTaskBullets(VBox bullets) {
    ArrayList<TaskBullet> tasks = new ArrayList<>();
    for (Node n : bullets.getChildren()) {
      for (Map.Entry<Bullet, Button> entries : taskMap.entrySet()) {
        if (entries.getValue().equals(n)) {
          Bullet b = entries.getKey();
          tasks.add((TaskBullet) b);
        }
      }
    }
    return tasks;
  }

  /**
   * Creates a day json by taking eventBullets and taskBullets frmo a day
   *
   * @param eventBullets The eventbullets on a given day
   * @param taskBullets  The task bullets on a given day
   * @return A dayJson
   */
  private DayJson dayToJson(List<EventBullet> eventBullets, List<TaskBullet> taskBullets) {
    return new DayJson(eventBulletsToJson(eventBullets), taskBulletsToJson(taskBullets));
  }

  /**
   * Takes a list of eventBullets and converts it into a list of eventJsons
   *
   * @param eventBullets A list of eventBullets
   * @return A list of eventJsons
   */
  private List<EventJson> eventBulletsToJson(List<EventBullet> eventBullets) {
    List<EventJson> eventJsons = new ArrayList<>();

    for (EventBullet eb : eventBullets) {
      eventJsons.add(eventToJson(eb));
    }

    return eventJsons;
  }

  /**
   * Takes a list of taskBullets and converts it into a list of taskJsons
   *
   * @param taskBullets A list of taskBullets
   * @return A list of taskJsons
   */
  private List<TaskJson> taskBulletsToJson(List<TaskBullet> taskBullets) {
    List<TaskJson> taskJsons = new ArrayList<>();

    for (TaskBullet tb : taskBullets) {
      taskJsons.add(taskToJson(tb));
    }

    return taskJsons;
  }

  /**
   * Converts an event to an eventJson
   *
   * @param eventBullet an event bullet to convert
   * @return The eventJson of the eventBullet
   */
  private EventJson eventToJson(EventBullet eventBullet) {
    return new EventJson(eventBullet.getName(), eventBullet.getDescription(),
        eventBullet.getDayOfTheWeek(),
        categoryToJson(eventBullet.getCategory()), timeToJson(eventBullet.getStartTime()),
        eventBullet.getDuration());
  }

  /**
   * Converts a time object to a time json
   *
   * @param time a time object
   * @return a time json
   */
  private TimeJson timeToJson(Time time) {
    return new TimeJson(time.getHour(), time.getMinute(), time.getMeridian());
  }

  /**
   * Converts a task to a task Json
   *
   * @param taskBullet a taskBullet
   * @return a taskJson
   */
  private TaskJson taskToJson(TaskBullet taskBullet) {
    return new TaskJson(taskBullet.getName(), taskBullet.getDescription(),
        taskBullet.getDayOfTheWeek(),
        categoryToJson(taskBullet.getCategory()), taskBullet.getIsComplete());
  }

  /**
   * Converts the list of allCategories into a list of category jsons
   *
   * @return The list of category jsons
   */
  private List<CategoryJson> allCategoriesToJson() {
    List<CategoryJson> categoryJsons = new ArrayList<>();

    for (Category c : categories) {
      categoryJsons.add(categoryToJson(c));
    }

    return categoryJsons;
  }

  /**
   * Converts a category to a categoryJson
   *
   * @param c A category
   * @return A categoryJson
   */
  private CategoryJson categoryToJson(Category c) {
    return new CategoryJson(c.getName(), c.getColor().getRed(), c.getColor().getGreen(),
        c.getColor().getBlue());
  }

  /**
   * Sets the startWith day to the given day
   *
   * @param startsWith the day to start with
   */
  public void setStartsWith(Day startsWith) {
    this.startsWith = startsWith;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

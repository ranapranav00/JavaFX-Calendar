package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.JournalModel;
import cs3500.pa05.model.bullets.Bullet;
import cs3500.pa05.model.bullets.EventBullet;
import cs3500.pa05.model.bullets.TaskBullet;
import cs3500.pa05.model.json.JsonUtils;
import cs3500.pa05.view.JournalView;
import cs3500.pa05.view.JournalViewImpl;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * represents a controller for the journal
 */
public class JournalControllerImpl implements JournalController {

  private WeekLoaderController weekLoaderController;
  private JournalModel journal;
  private Map<Bullet, Button> eventBulletMap;
  private Map<Bullet, Button> taskBulletMap;
  private NewEventController newEventController;
  private NewTaskController newTaskController;
  private WeekSaverController weekSaverController;
  private SetPasswordController setPasswordController;
  private Day startsWith;
  private List<Category> allCategories;
  private Map<Day, Integer> eventsInDayMap;
  private Map<Day, Integer> tasksInDayMap;
  private boolean isVerify;
  @FXML
  private HBox statistics;
  @FXML
  private Label weekName;
  @FXML
  private Button newEvent;
  @FXML
  private Button newTask;
  @FXML
  private Button newWeek;
  @FXML
  private Label numEvents;
  @FXML
  private Label numTasks;
  @FXML
  private ProgressBar tasksCompleted;
  @FXML
  private Button save;
  @FXML
  private Button setPassword;
  @FXML
  private HBox allDays;
  @FXML
  private VBox sunday;
  @FXML
  private VBox monday;
  @FXML
  private VBox tuesday;
  @FXML
  private VBox wednesday;
  @FXML
  private VBox thursday;
  @FXML
  private VBox friday;
  @FXML
  private VBox saturday;
  @FXML
  private ChoiceBox<String> layout;
  @FXML
  private Button open;
  @FXML
  private VBox sundayBullets;
  @FXML
  private VBox mondayBullets;
  @FXML
  private VBox tuesdayBullets;
  @FXML
  private VBox wednesdayBullets;
  @FXML
  private VBox thursdayBullets;
  @FXML
  private VBox fridayBullets;
  @FXML
  private VBox saturdayBullets;
  @FXML
  private Scene scene;
  @FXML
  private Label displayMaxEvents;
  @FXML
  private Label displayMaxTasks;

  /**
   * constructor for JournalControllerImpl when creating a new week
   *
   * @param journal the journal of this week
   */
  public JournalControllerImpl(JournalModel journal) {
    this.journal = Objects.requireNonNull(journal);
    this.eventBulletMap = new HashMap<>();
    this.taskBulletMap = new HashMap<>();
  }

  /**
   * constructor for JournalControllerImpl when loading a week from a
   * pre-existing file
   *
   * @param bujoFile the file path to the pre-existing week
   */
  public JournalControllerImpl(File bujoFile) {
    this.weekLoaderController = new WeekLoaderController(bujoFile);
  }

  /**
   * runs the journal controller
   */
  @Override
  public void run() {
    this.allCategories = new ArrayList<>();
    initButtons();
    initJournal();
    initControllers();
    initShortcuts();
    initPassword();
  }

  /**
   * initializes the setup for the screen if there is a prexisitng password
   */
  private void initPassword() {
    if (!(journal.getPassword().equals(""))) {
      isVerify = true;
      weekName.setText("ENTER THE PASSWORD");
      weekName.setFont(Font.font("System", FontWeight.BOLD, 36));
      weekName.setTextFill(Color.RED);
      newEvent.setVisible(false);
      newTask.setVisible(false);
      newWeek.setVisible(false);
      save.setVisible(false);
      open.setVisible(false);
      layout.setVisible(false);
      statistics.setVisible(false);
      allDays.setVisible(false);
      setPassword.setText("CLICK HERE");
      setPassword.setTextFill(Color.RED);
    }
  }

  /**
   * initializes the new event, new task, and save buttons
   */
  private void initButtons() {
    newEvent.setOnAction(event -> handleNewEvent());
    newTask.setOnAction(event -> handleNewTask());
    save.setOnAction(event -> handleSave());
    setPassword.setOnAction(event -> handleSetPassword());
  }

  /**
   * sets the color of each event or task button in the week view
   */
  private void colorButtons() {
    for (Bullet b : eventBulletMap.keySet()) {
      eventBulletMap.get(b)
          .setStyle("-fx-background-color: " + torgbCode(b.getCategory().getColor()) + ";");
    }

    for (Bullet b : taskBulletMap.keySet()) {
      taskBulletMap.get(b)
          .setStyle("-fx-background-color: " + torgbCode(b.getCategory().getColor()) + ";");
    }
  }

  /**
   * initializes the journal
   */
  private void initJournal() {
    if (weekLoaderController != null) {
      eventBulletMap = weekLoaderController.populateEventMap();
      taskBulletMap = weekLoaderController.populateTaskMap();
      startsWith = weekLoaderController.getStartsWith();
      allCategories = weekLoaderController.populateAllCategories();
      colorButtons();
      handleStartWith(startsWith.toString());
      setEventBulletHandler();
      setTaskBulletHandler();
      journal = new JournalModel(weekLoaderController.getName(),
          weekLoaderController.getMaxEvents(), weekLoaderController.getMaxTasks(),
          weekLoaderController.getPassword());
      populateJournal();
    }
    this.weekName.setText(journal.getName());
    this.displayMaxEvents.setText(String.valueOf(journal.getMaxEvents()));
    this.displayMaxTasks.setText(String.valueOf(journal.getMaxTasks()));
    this.startsWith = Day.SUNDAY;
    this.numEvents.setText("0");
    this.numTasks.setText("0");
    this.tasksCompleted.setStyle("-fx-text-box-border: #370152");
    this.tasksCompleted.setStyle("-fx-control-inner-background: rgba(198,66,255,0.58)");
    this.eventsInDayMap = new HashMap<>();
    this.tasksInDayMap = new HashMap<>();
    for (Day d : Day.values()) {
      eventsInDayMap.put(d, 0);
      tasksInDayMap.put(d, 0);
    }

    layout.getItems().addAll("Starts with", "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday");

    layout.getSelectionModel().select(0);
    layout.getSelectionModel().selectedItemProperty().addListener(
        (ObservableValue<? extends String> observable, String oldDay, String newDay)
            -> handleStartWith(newDay));
  }

  /**
   * initializes the necessary controllers for the journal
   */
  private void initControllers() {
    this.newEventController = new NewEventController(journal.getMaxEvents(), eventsInDayMap);
    this.newTaskController = new NewTaskController(journal.getMaxTasks(), tasksInDayMap);
    this.setPasswordController = new SetPasswordController(journal);

    ArrayList<VBox> weekVboxes =
        new ArrayList<>(List.of(sundayBullets, mondayBullets, tuesdayBullets,
            wednesdayBullets, thursdayBullets, fridayBullets, saturdayBullets));
    this.weekSaverController =
        new WeekSaverController(journal, startsWith, allCategories, weekVboxes, eventBulletMap,
            taskBulletMap);
  }

  /**
   * initializes keyboard shortcuts
   */
  private void initShortcuts() {
    ObservableMap<KeyCombination, Runnable> shortcuts = scene.getAccelerators();

    KeyCombination createEvent = new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
    Runnable createEventAction = this::handleNewEvent;
    KeyCombination createTask = new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN);
    Runnable createTaskAction = this::handleNewTask;
    KeyCombination createNewWeek = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);
    Runnable createNewWeekAction = this::handleNewEvent;
    KeyCombination saveFile = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
    Runnable saveFileAction = this::handleSave;
    KeyCombination password = new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN);
    Runnable passwordAction = this::handleSetPassword;
    KeyCombination newBothBullets = new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN);
    Runnable bothBulletsAction = () -> {
      handleNewEvent();
      handleNewTask();
    };

    shortcuts.put(createEvent, createEventAction);
    shortcuts.put(createTask, createTaskAction);
    shortcuts.put(createNewWeek, createNewWeekAction);
    shortcuts.put(saveFile, saveFileAction);
    shortcuts.put(password, passwordAction);
    shortcuts.put(newBothBullets, bothBulletsAction);
  }

  /**
   * gets the new week button
   *
   * @return the new week button
   */
  public Button getNewWeek() {
    return newWeek;
  }

  /**
   * gets the open button
   *
   * @return the open button
   */
  public Button getOpen() {
    return open;
  }

  /**
   * Handles the setting of the password based on if it has been previously verified
   */
  private void handleSetPassword() {
    JournalView passwordView = new JournalViewImpl(setPasswordController, "SetPassword.fxml");
    Stage substage = new Stage();
    substage.setScene(passwordView.load());
    setPasswordController.run();
    substage.show();

    Button createButton = setPasswordController.getCreateButton();

    if (isVerify) {
      setPasswordController.getCreateNewPassword().setDisable(true);
      createButton.setOnAction(event -> handleVerifyPassword(setPasswordController, substage));
    } else {
      setPasswordController.getCreateNewPassword().setDisable(false);
      createButton.setOnAction(
          event -> handlePasswordCreatePressed(setPasswordController, substage));
    }
  }

  /**
   * Handles the verification of the password when the program starts
   *
   * @param cont     : the SetPasswordController that is used
   * @param substage : The stage that the password is shown on
   */
  private void handleVerifyPassword(SetPasswordController cont, Stage substage) {
    isVerify = false;
    this.weekName.setText(journal.getName());
    weekName.setFont(Font.font("System", FontWeight.BOLD, 36));
    weekName.setTextFill(Color.WHITE);
    newEvent.setVisible(true);
    newTask.setVisible(true);
    newWeek.setVisible(true);
    save.setVisible(true);
    open.setVisible(true);
    layout.setVisible(true);
    statistics.setVisible(true);
    allDays.setVisible(true);
    setPassword.setText("Set Password");
    setPassword.setTextFill(Color.WHITE);
    setPassword.setBackground(Background.EMPTY);
    substage.close();
  }

  /**
   * Handles the verification of the password after the program has started
   *
   * @param cont     the password controller
   * @param substage the stage of the password controller
   */
  private void handlePasswordCreatePressed(SetPasswordController cont, Stage substage) {
    substage.close();
    cont.getPassword();
    System.out.println(journal.getPassword());
  }


  /**
   * handles the creation of a new event
   */
  private void handleNewEvent() {
    JournalView journalView = new JournalViewImpl(newEventController, "NewEvent.fxml");

    Stage substage = new Stage();
    substage.setScene(journalView.load());
    newEventController.setCategoryDropdown(allCategories);
    newEventController.run();
    substage.show();

    Button createButton = newEventController.getCreateButton();

    createButton.setOnAction(event -> handleEventCreatePressed(newEventController, substage));

    newEventController.disable();
  }

  /**
   * handles actions for when the create button for a new event is pressed
   *
   * @param newEventController the controller to create a new event
   * @param substage           the stage of the new event dialog
   */
  private void handleEventCreatePressed(NewEventController newEventController, Stage substage) {
    substage.close();
    Bullet newBullet = newEventController.getBullet();
    Button newBulletButton = new Button(newBullet.getName());
    newBulletButton.setPrefWidth(sunday.getPrefWidth());
    updateCategory(newBullet, newBulletButton);
    eventBulletMap.put(newBullet, newBulletButton);
    Day day = newBullet.getDayOfTheWeek();
    eventsInDayMap.put(day, eventsInDayMap.get(day) + 1);

    addBulletsToWeek(newBullet, newBulletButton);
    setEventBulletHandler();
    updateCounts();
  }

  /**
   * converts a color to a rgb code
   *
   * @param color color to convert
   * @return the rgb code
   */
  private String torgbCode(Color color) {
    return String.format("#%02x%02x%02x",
        (int) (color.getRed() * 255),
        (int) (color.getGreen() * 255),
        (int) (color.getBlue() * 255));
  }

  /**
   * sets the event handlers for every event bullet
   */
  private void setEventBulletHandler() {
    for (Map.Entry<Bullet, Button> tempMap : eventBulletMap.entrySet()) {
      Bullet bullet = tempMap.getKey();
      Button button = tempMap.getValue();

      button.setOnAction(event -> handleEventBulletClicked(bullet, button));
    }
  }


  /**
   * handles actions for when the new task button is pressed
   */
  private void handleNewTask() {
    JournalView journalView = new JournalViewImpl(newTaskController, "NewTask.fxml");

    Stage substage = new Stage();
    substage.setScene(journalView.load());
    newTaskController.setCategoryDropdown(allCategories);
    newTaskController.run();
    substage.show();

    Button createButton = newTaskController.getCreateButton();
    createButton.setOnAction(event -> handleTaskCreatePressed(newTaskController, substage));

    newTaskController.disable();
  }

  /**
   * handles actions for when the create button for a new task is pressed
   *
   * @param newTaskController the controller for creating a new task
   * @param substage          the stage
   */
  private void handleTaskCreatePressed(NewTaskController newTaskController, Stage substage) {
    substage.close();
    TaskBullet newBullet = newTaskController.getBullet();
    Button newBulletButton = new Button(newBullet.getName());
    newBulletButton.setPrefWidth(sunday.getPrefWidth());
    updateCategory(newBullet, newBulletButton);
    taskBulletMap.put(newBullet, newBulletButton);
    Day day = newBullet.getDayOfTheWeek();
    tasksInDayMap.put(day, tasksInDayMap.get(day) + 1);
    addBulletsToWeek(newBullet, newBulletButton);
    setTaskBulletHandler();
    updateCounts();
  }

  /**
   * sets the event handlers for every task
   */
  private void setTaskBulletHandler() {
    for (Map.Entry<Bullet, Button> tempMap : taskBulletMap.entrySet()) {
      Bullet bullet = tempMap.getKey();
      Button button = tempMap.getValue();

      button.setOnAction(event -> handleTaskBulletClicked(bullet, button));
    }
  }

  /**
   * handles actions for when the save button is pressed
   */
  private void handleSave() {
    weekSaverController.setStartsWith(startsWith);
    weekSaverController.setPassword(journal.getPassword());
    JsonNode weekJson = JsonUtils.serializeRecordToJson(weekSaverController.weekToJson());
    File bujoFile = new File("src/main/outputs/" + weekName.getText() + ".bujo");
    byte[] data = weekJson.toString().getBytes();
    Path destination = bujoFile.toPath();

    try {
      Files.write(destination, data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * handles actions for when an event bullet is clicked
   *
   * @param eventBullet the event bullet
   * @param eventButton the clicked button
   */
  private void handleEventBulletClicked(Bullet eventBullet, Button eventButton) {
    MiniViewerController eventController = new MiniViewerEventController((EventBullet) eventBullet,
        journal.getMaxEvents(), eventsInDayMap);
    JournalView miniViewerEventView = new JournalViewImpl(eventController, "EventView.fxml");

    Stage subStage = new Stage();
    subStage.setScene(miniViewerEventView.load());
    eventController.setCategoryDropdown(allCategories); // CATEGORY
    eventController.run();
    subStage.show();

    Button saveButton = eventController.getCreateButton();
    Button deleteButton = eventController.getDeleteButton();
    Day day = eventBullet.getDayOfTheWeek();

    saveButton.setOnAction(
        event -> {
          handleBulletSaved(eventController, eventBullet, eventButton, subStage);
          eventsInDayMap.put(day, eventsInDayMap.get(day) + 1);
        });

    deleteButton.setOnAction(
        event -> {
          handleBulletDeleted(eventBulletMap, eventBullet, eventButton, subStage);
          eventsInDayMap.put(day, eventsInDayMap.get(day) - 1);
        });
  }

  /**
   * handles actions for when a task bullet is clicked
   *
   * @param taskBullet the task bullet
   * @param taskButton the clicked button
   */
  private void handleTaskBulletClicked(Bullet taskBullet, Button taskButton) {
    MiniViewerController taskController = new MiniViewerTaskController((TaskBullet) taskBullet,
        journal.getMaxTasks(), tasksInDayMap);
    JournalView miniViewerTaskView = new JournalViewImpl(taskController, "TaskView.fxml");

    Stage subStage = new Stage();
    subStage.setScene(miniViewerTaskView.load());
    taskController.setCategoryDropdown(allCategories);
    taskController.run();
    subStage.show();

    Button saveButton = taskController.getCreateButton();
    Button deleteButton = taskController.getDeleteButton();
    Day day = taskBullet.getDayOfTheWeek();

    saveButton.setOnAction(
        event -> {
          handleBulletSaved(taskController, taskBullet, taskButton, subStage);
          tasksInDayMap.put(day, tasksInDayMap.get(day) + 1);
        });

    deleteButton.setOnAction(
        event -> {
          handleBulletDeleted(taskBulletMap, taskBullet, taskButton, subStage);
          tasksInDayMap.put(day, tasksInDayMap.get(day) - 1);
        });
  }

  /**
   * handles actions for when a bullet is saved in the mini viewer
   *
   * @param controller the mini viewer controller
   * @param bullet     the bullet being saved
   * @param button     the button for the bullet
   * @param stage      the stage of the mini viewer
   */
  private void handleBulletSaved(MiniViewerController controller, Bullet bullet, Button button,
                                 Stage stage) {

    removeBulletsFromWeek(bullet, button);
    stage.close();
    bullet = controller.getBullet();
    button.setText(bullet.getName());
    updateCategory(bullet, button);
    addBulletsToWeek(bullet, button);
    updateCounts();
  }

  /**
   * handles actions for when a bullet is deleted in the mini viewer
   *
   * @param bulletMap the map of bullets to buttons
   * @param bullet    the bullet being deleted
   * @param button    the button for the bullet
   * @param stage     the stage of the mini viewer
   */
  private void handleBulletDeleted(Map<Bullet, Button> bulletMap, Bullet bullet, Button button,
                                   Stage stage) {
    stage.close();
    bulletMap.remove(bullet);
    removeBulletsFromWeek(bullet, button);
    updateCounts();
  }

  /**
   * adds the given button of the bullet to the appropriate day in the week view
   *
   * @param newBullet       the bullet being added
   * @param newBulletButton the button of the bullet
   */
  private void addBulletsToWeek(Bullet newBullet, Button newBulletButton) {
    switch (newBullet.getDayOfTheWeek()) {
      case SUNDAY -> sundayBullets.getChildren().add(newBulletButton);
      case MONDAY -> mondayBullets.getChildren().add(newBulletButton);
      case TUESDAY -> tuesdayBullets.getChildren().add(newBulletButton);
      case WEDNESDAY -> wednesdayBullets.getChildren().add(newBulletButton);
      case THURSDAY -> thursdayBullets.getChildren().add(newBulletButton);
      case FRIDAY -> fridayBullets.getChildren().add(newBulletButton);
      case SATURDAY -> saturdayBullets.getChildren().add(newBulletButton);
      default -> {

      }
    }
  }

  /**
   * removes the given button of the bullet from the appropriate day in the week view
   *
   * @param bullet       the bullet being removed
   * @param bulletButton the button of the bullet being removed
   */
  private void removeBulletsFromWeek(Bullet bullet, Button bulletButton) {
    switch (bullet.getDayOfTheWeek()) {
      case SUNDAY -> sundayBullets.getChildren().remove(bulletButton);
      case MONDAY -> mondayBullets.getChildren().remove(bulletButton);
      case TUESDAY -> tuesdayBullets.getChildren().remove(bulletButton);
      case WEDNESDAY -> wednesdayBullets.getChildren().remove(bulletButton);
      case THURSDAY -> thursdayBullets.getChildren().remove(bulletButton);
      case FRIDAY -> fridayBullets.getChildren().remove(bulletButton);
      case SATURDAY -> saturdayBullets.getChildren().remove(bulletButton);
      default -> {
      }
    }
  }

  /**
   * handles actions for the starts with dropdown is clicked
   *
   * @param dayName the day that the week will start with
   */
  private void handleStartWith(String dayName) {
    allDays.getChildren().clear();
    int split = 0;
    ArrayList<VBox> days =
        new ArrayList<>(
            Arrays.asList(sunday, monday, tuesday, wednesday, thursday, friday, saturday));

    switch (dayName.toUpperCase()) {
      case "MONDAY" -> {
        split = 1;
        startsWith = Day.MONDAY;
      }
      case "TUESDAY" -> {
        split = 2;
        startsWith = Day.TUESDAY;
      }
      case "WEDNESDAY" -> {
        split = 3;
        startsWith = Day.WEDNESDAY;
      }
      case "THURSDAY" -> {
        split = 4;
        startsWith = Day.THURSDAY;
      }
      case "FRIDAY" -> {
        split = 5;
        startsWith = Day.FRIDAY;
      }
      case "SATURDAY" -> {
        split = 6;
        startsWith = Day.SATURDAY;
      }
      default -> {
      }
    }

    List<VBox> end = days.subList(0, split);
    List<VBox> start = days.subList(split, 7);
    start.addAll(end);

    for (VBox v : start) {
      allDays.getChildren().add(v);
    }
  }

  /**
   * Updates the count of the completed tasks and percentage when necessary
   */
  private void updateCounts() {
    double completedTasks = 0.0;
    double completedPercentage = 0.0;

    for (Bullet b : taskBulletMap.keySet()) {
      TaskBullet taskBullet = (TaskBullet) b;
      if (taskBullet.getIsComplete()) {
        completedTasks++;
      }
    }

    if (taskBulletMap.size() != 0) {
      completedPercentage = completedTasks / taskBulletMap.size();
      tasksCompleted.setProgress(completedPercentage);
      tasksCompleted.setStyle("-fx-acent: #370152;");
    }

    numEvents.setText(String.valueOf(eventBulletMap.size()));
    numTasks.setText(String.valueOf(taskBulletMap.size()));
  }

  /**
   * Populates the journal bullets when loading from a pre-existing bujo
   */
  private void populateJournal() {
    populateDayBullets(Day.SUNDAY, sundayBullets, eventBulletMap);
    populateDayBullets(Day.SUNDAY, sundayBullets, taskBulletMap);
    populateDayBullets(Day.MONDAY, mondayBullets, eventBulletMap);
    populateDayBullets(Day.MONDAY, mondayBullets, taskBulletMap);
    populateDayBullets(Day.TUESDAY, tuesdayBullets, eventBulletMap);
    populateDayBullets(Day.TUESDAY, tuesdayBullets, taskBulletMap);
    populateDayBullets(Day.WEDNESDAY, wednesdayBullets, eventBulletMap);
    populateDayBullets(Day.WEDNESDAY, wednesdayBullets, taskBulletMap);
    populateDayBullets(Day.THURSDAY, thursdayBullets, eventBulletMap);
    populateDayBullets(Day.THURSDAY, thursdayBullets, taskBulletMap);
    populateDayBullets(Day.FRIDAY, fridayBullets, eventBulletMap);
    populateDayBullets(Day.FRIDAY, fridayBullets, taskBulletMap);
    populateDayBullets(Day.SATURDAY, saturdayBullets, eventBulletMap);
    populateDayBullets(Day.SATURDAY, saturdayBullets, taskBulletMap);
  }

  /**
   * Populates the dayBullets when opening a pre-existing .bujo file
   *
   * @param day        The day
   * @param dayBullets The bullets in said day
   * @param bulletMap  A map, either of event or task bullets
   */
  private void populateDayBullets(Day day, VBox dayBullets, Map<Bullet, Button> bulletMap) {
    for (Bullet b : bulletMap.keySet()) {
      if (b.getDayOfTheWeek().equals(day)) {
        dayBullets.getChildren().add(bulletMap.get(b));
      }
    }
  }

  /**
   * Updates a category color, and the colors of other bullets under said category
   *
   * @param bullet A bullet to update category for
   * @param button A button to update category for
   */
  private void updateCategory(Bullet bullet, Button button) {
    Category tempCategory = bullet.getCategory();
    if (!(alreadyExists(tempCategory))) {
      allCategories.add(tempCategory);
    } else {
      for (Category c : allCategories) {
        if (c.getName().equals(tempCategory.getName())) {
          c.setColor(tempCategory.getColor());
        }
      }
      for (Bullet b : eventBulletMap.keySet()) {
        if (b.getCategory().getName().equals(tempCategory.getName())) {
          b.getCategory().setColor(tempCategory.getColor());
          eventBulletMap.get(b)
              .setStyle("-fx-background-color: " + torgbCode(tempCategory.getColor()) + ";");
        }
      }
      for (Bullet b : taskBulletMap.keySet()) {
        b.getCategory().setColor(tempCategory.getColor());
        if (b.getCategory().getName().equals(tempCategory.getName())) {
          taskBulletMap.get(b)
              .setStyle("-fx-background-color: " + torgbCode(tempCategory.getColor()) + ";");
        }
      }
    }
    Color colorHex = bullet.getCategory().getColor();
    button.setStyle("-fx-background-color: " + torgbCode(colorHex) + ";");
  }

  /**
   * checks if a category already exists
   *
   * @param category A category
   * @return whether the category already exists
   */
  private boolean alreadyExists(Category category) {
    for (Category c : allCategories) {
      if (c.getName().equals(category.getName())) {
        return true;
      }
    }
    return false;
  }
}

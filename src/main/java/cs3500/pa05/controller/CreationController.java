package cs3500.pa05.controller;

import cs3500.pa05.model.JournalModel;
import cs3500.pa05.view.JournalView;
import cs3500.pa05.view.JournalViewImpl;
import java.io.File;
import java.nio.file.Path;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * represents a controller for the creation of weeks
 */
public class CreationController implements MainController {

  private int count;
  private Stage welcomeStage;
  private Stage currentWeekStage;
  private NewWeekController newWeekController;

  @FXML
  private AnchorPane rootPane;
  @FXML
  private Label inputBujo;
  @FXML
  private Button select;
  @FXML
  private TextField weekName;
  @FXML
  private TextField maxEvents;
  @FXML
  private TextField maxTasks;
  @FXML
  private Button clear;
  @FXML
  private Button create;

  /**
   * Constructor for creation controller
   *
   * @param welcomeStage the welcome stage
   */
  public CreationController(Stage welcomeStage) {
    this.welcomeStage = welcomeStage;
    this.count = 0;
  }

  /**
   * runs the creation controller
   */
  public void run() {
    initButtons();
  }

  /**
   * initializes the buttons on the welcome screen and disables the create button
   * until input fields are valid
   */
  private void initButtons() {
    select.setOnAction(event -> handleOpen());
    clear.setOnAction(event -> resetFields());
    create.setOnAction(event -> handleWelcomeCreate());
    restrictFields();
    disable();
  }

  /**
   * loads the splash screen
   */
  private void loadSplashScreen() {

    AnchorPane pane = new AnchorPane();
    pane.setPrefSize(500, 360);
    pane.setBackground(Background.fill(Paint.valueOf("BLACk")));

    HBox horizText = new HBox();
    horizText.setPrefSize(500, 360);
    horizText.setAlignment(Pos.CENTER);

    VBox vertText = new VBox();
    vertText.setPrefSize(500, 360);
    vertText.setAlignment(Pos.CENTER);

    Label label = new Label("Welcome to the Java Journal!");
    Label label2 = new Label("Loading your journal now...");
    label.setTextFill(Color.WHITE);
    label2.setTextFill(Color.LIGHTGREY);
    label.setFont(new Font("Courier New", 28));
    label2.setFont(new Font("Courier New", 20));

    vertText.getChildren().addAll(label, label2);
    horizText.getChildren().add(vertText);
    pane.getChildren().add(horizText);

    rootPane.getChildren().setAll(pane);

    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.75), pane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.setCycleCount(1);

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.2), pane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    fadeOut.setCycleCount(1);

    fadeIn.play();

    fadeIn.setOnFinished((event -> fadeOut.play()));

    fadeOut.setOnFinished(event -> {
      welcomeStage.close();
      currentWeekStage.show();
    });
  }

  /**
   * handles events for the create button on the welcome screen
   */
  private void handleWelcomeCreate() {
    JournalController journalController;
    loadSplashScreen();

    if (inputBujo.getText() != null) {
      if (!inputBujo.getText().isEmpty()) {
        journalController = new JournalControllerImpl(Path.of(inputBujo.getText()).toFile());
      } else {
        JournalModel journal =
            new JournalModel(weekName.getText(), Integer.parseInt(maxEvents.getText()),
                Integer.parseInt(maxTasks.getText()), "");
        journalController = new JournalControllerImpl(journal);
      }
    } else {
      JournalModel journal =
          new JournalModel(weekName.getText(), Integer.parseInt(maxEvents.getText()),
              Integer.parseInt(maxTasks.getText()), "");
      journalController = new JournalControllerImpl(journal);
    }

    JournalView journalView = new JournalViewImpl(journalController, "new - Copy.fxml");
    currentWeekStage = new Stage();
    currentWeekStage.setScene(journalView.load());
    journalController.getNewWeek().setOnAction(event -> handleNewWeek());
    journalController.getOpen().setOnAction(event -> handleOpen());
    journalController.run();

    count++;
  }

  /**
   * handles events for the create button in the week view
   */
  private void handleWeekCreate() {
    currentWeekStage.close();

    JournalModel journal = new JournalModel(newWeekController.getName(),
        newWeekController.getMaxEvents(), newWeekController.getMaxTasks(), "");

    JournalController journalController = new JournalControllerImpl(journal);

    Stage newWeekStage = new Stage();
    JournalView journalView = new JournalViewImpl(journalController, "new - Copy.fxml");
    Scene scene = journalView.load();
    newWeekStage.setScene(scene);
    journalController.getNewWeek().setOnAction(event -> handleNewWeek());
    journalController.getOpen().setOnAction(event -> handleOpen());
    journalController.run();
    newWeekStage.show();
    currentWeekStage = newWeekStage;
    resetFields();
  }

  /**
   * handles the new week button on the week view
   */
  private void handleNewWeek() {
    newWeekController = new NewWeekControllerImpl();
    JournalView journalView = new JournalViewImpl(newWeekController, "NewWeek.fxml");
    Stage substage = new Stage();
    substage.setScene(journalView.load());
    newWeekController.run();
    substage.show();
    Button createButton = newWeekController.getCreateButton();
    createButton.setOnAction(event -> handleWeekCreate());
  }

  /**
   * handles the open button on the week view
   */
  private void handleOpen() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open a .bujo File!");
    fileChooser.setInitialDirectory(new File("src/main/outputs"));
    File selectedFile = fileChooser.showOpenDialog(welcomeStage);
    if (selectedFile != null) {
      inputBujo.setText(selectedFile.getPath());
      if (count > 0) {
        handleWelcomeCreate();
      }
    }
  }

  /**
   * disables the create button on the welcome screen until
   * input fields are valid
   */
  private void disable() {
    BooleanBinding fileNotInputted = Bindings.isEmpty(inputBujo.textProperty());
    BooleanBinding newStatsNotInputted = Bindings.isEmpty(weekName.textProperty())
        .or(Bindings.isEmpty(maxEvents.textProperty())
            .or(Bindings.isEmpty(maxTasks.textProperty())));
    BooleanBinding fileInputted = Bindings.isNotEmpty(inputBujo.textProperty());
    BooleanBinding newStatsInputted = Bindings.isNotEmpty(weekName.textProperty())
        .or(Bindings.isNotEmpty(maxEvents.textProperty())
            .or(Bindings.isNotEmpty(maxTasks.textProperty())));

    create.disableProperty().bind(fileNotInputted.and(newStatsNotInputted)
        .or(fileInputted.and(newStatsInputted)));

    clear.disableProperty().bind(fileNotInputted.and(newStatsNotInputted));
  }

  private void restrictFields() {
    maxEvents.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        maxEvents.setText(newValue.replaceAll("\\D", ""));
      }
    });

    maxTasks.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        maxTasks.setText(newValue.replaceAll("\\D", ""));
      }
    });
  }

  /**
   * resets the input fields on the welcome screen
   */
  private void resetFields() {
    inputBujo.setText("");
    weekName.setText("");
    maxEvents.setText("");
    maxTasks.setText("");
  }
}


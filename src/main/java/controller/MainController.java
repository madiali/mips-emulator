package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mips.Mips;
import mips.ProgramLoader;
import mips.Registers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXML is tied to this Controller and only this controller. All handle methods (handleOpen,
 * handleTabClick, etc.) must go in here.
 *
 * <p>Implement Initializable to call initialize() method - user is forced to load a JSON at
 * startup. mips won't be null at startup. This is a bit lazy but prevents us from having to worry
 * about edge cases related to a JSON not being open. TODO: Improve this lol
 */
public class MainController implements Initializable {
  private Mips mips;
  // FileChooser uses Stage for some reason.
  private Stage stage;
  // Encapsulate other controllers since all handle methods must be called in this file?
  // Or make AccelerometerController's methods static?
  private AccelerometerController accelControl;
  //  private VgaDisplayController vgaDispControl;
  private VgaDisplayBMPController vgaDispBMPControl;
  private MenuController menuController;
  private RegistersController registersController;
  private InstructionMemoryController instructionMemoryController;
  private DataMemoryController dataMemoryController;
  private KeyboardController keyboardController;
  private static boolean isExecuting;

  // @FXML tags are **necessary** for the variables to be automatically linked to FXML components.
  // Menu
  @FXML private MenuItem open;
  @FXML private MenuItem exit;
  @FXML private MenuItem go;
  @FXML private MenuItem pause;
  @FXML private MenuItem stepForward;

  // Accelerometer
  @FXML private Slider xSlider;
  @FXML private Slider ySlider;
  @FXML private Label xLabel;
  @FXML private Label yLabel;
  @FXML private Button resetButton;

  // Screen
  @FXML private GridPane vgaDisplay;

  // Debugging tabs
  @FXML private TableView registersTable;
  @FXML private TableView instructionMemoryTable;
  @FXML private TableView dataMemoryTable;

  public MainController(Mips mips) {
    if (mips == null) {
      throw new IllegalArgumentException("Mips is null");
    }
    this.mips = mips;
  }

  /** A MipsController with no params is necessary for loader.getController() in AppLauncher. */
  public MainController() {}

  public int getPC() {
    return mips.getPC();
  }

  public String getName() {
    return mips.getName();
  }

  public float getClockSpeed() {
    return mips.getClockSpeed();
  }

  public void setClockSpeed(float newClockSpeed) {
    mips.setClockSpeed(newClockSpeed);
  }

  public void executeNext() {
    mips.executeNext();
  }

  public void executeAll() {
    mips.executeAll();
  }

  public int getReg(int regNum) {
    return mips.getReg().getRegister(regNum);
  }

  public void setReg(int regNum, int val) {
    mips.getReg().setRegister(regNum, val);
  }

  public void regToName(int regNum) {
    Registers.registerToName(regNum);
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /*
   * Menu
   */

  @FXML
  public void handleOpen() throws IOException {
    // Prompt for project JSON
    FileChooser fc = new FileChooser();
    fc.setTitle("Open project JSON");
    fc.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Project configuration JSON file", "*.json"));
    File selectedFile = fc.showOpenDialog(this.stage);

    // Model instantiation
    ProgramLoader pl = new ProgramLoader(selectedFile);
    this.mips = pl.getMips();

    // Controller instantiation
    this.accelControl =
        new AccelerometerController(mips, xSlider, ySlider, xLabel, yLabel, resetButton);
    this.vgaDispBMPControl = new VgaDisplayBMPController(mips, vgaDisplay);
    this.menuController = new MenuController(mips, open, exit, go, pause, stepForward);
    this.registersController = new RegistersController(mips, registersTable);
    this.instructionMemoryController =
        new InstructionMemoryController(mips, instructionMemoryTable);
    this.dataMemoryController = new DataMemoryController(mips, dataMemoryTable);

    this.keyboardController = new KeyboardController(mips);

  }

  /**
   * Set isExecuting to false to halt execution in ExecuteAll to get time statistics.
   */
  @FXML
  public void handleExit() {
    isExecuting = false;
  }

  /**
   * Incomplete implementation.
   */
  @FXML
  public void handleGo() {
    isExecuting = true;
    // Just for testing
    ExecuteAll ea = new ExecuteAll(mips);
    Thread t = new Thread(ea);
    t.start();
  }

  /**
   * Set isExecuting to false to pause execution in ExecuteAll to print out time statistics.
   * Does the same thing as Exit button, for now.
   */
  @FXML
  public void handlePause() {
    isExecuting = false;
    RegistersController.renderRegisterTable();
    DataMemoryController.renderDataMemoryTable();
  }

  @FXML
  public void handleStepForward() {
    mips.executeNext();
  }

  /*
   * Accelerometer
   */

  @FXML
  public void handleXSliderDrag() {
    accelControl.handleXSliderDrag();
  }

  @FXML
  public void handleYSliderDrag() {
    accelControl.handleYSliderDrag();
  }

  @FXML
  public void handleResetButton() {
    accelControl.handleResetButton();
  }

  /*
   * Keyboard
   */

  public void handleOnKeyDown(KeyCode keycode) {
    keyboardController.handleOnKeyDown(keycode);
  }

  public void handleOnKeyUp(KeyCode keycode) {
    keyboardController.handleOnKeyUp(keycode);
  }

  public static boolean getIsExecuting() {
    return isExecuting;
  }

  public static void setIsExecuting(boolean value) {
    isExecuting = value;
  }

  public static void renderAllDisplays() {
    VgaDisplayBMPController.renderVGA();
    RegistersController.renderRegisterTable();
    DataMemoryController.renderDataMemoryTable();
  }

  /**
   * This method is called at startup, so user is prompted for JSON at startup. This prevents us
   * from having to worry about edge cases that happen when a JSON is not loaded.
   *
   * <p>TODO: We can improve this (without JSON, show app and gray out relevant buttons, etc.)
   * later.
   *
   * @param url
   * @param resourceBundle
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      handleOpen();
    } catch (IOException ioe) {
      throw new IllegalArgumentException(
          "Your JSON file doesn't exist or something else went wrong during initialization");
    }
  }
}

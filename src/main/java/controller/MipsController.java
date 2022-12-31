package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
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
 * about edge cases related to a JSON not being open.
 */
public class MipsController implements Initializable {
  private Mips mips;
  // FileChooser uses Stage for some reason.
  private Stage stage;
  // Encapsulate other controllers since all handle methods must be called in this file?
  // Or make AccelerometerController's methods static?
  private AccelerometerController accelControl;
  //  private VgaDisplayController vgaDispControl;
  private VgaDisplayBMPController vgaDispBMPControl;
  private MenuController menuController;

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

  public MipsController(Mips mips) {
    if (mips == null) {
      throw new IllegalArgumentException("Mips is null");
    }
    this.mips = mips;
  }

  /** A MipsController with no params is necessary for loader.getController() in AppLauncher. */
  public MipsController() {}

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

  // If we do have multiple Controllers encapsulated in this class, handleOpen needs to
  // reinstantiate all of them.
  @FXML
  public void handleOpen() throws IOException {
    // Prompt user for project JSON
    FileChooser fc = new FileChooser();
    fc.setTitle("Open project JSON");
    fc.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Project configuration JSON file", "*.json"));
    File selectedFile = fc.showOpenDialog(this.stage);

    // Model instantiation
    ProgramLoader pl = new ProgramLoader(selectedFile);
    this.mips = pl.getMips();

    // Controller instantiation
    // Pass mips (can't be done in MipsController constructor) and FXML components to other
    // Controller files through constructor.
    this.accelControl =
        new AccelerometerController(this.mips, xSlider, ySlider, xLabel, yLabel, resetButton);
    this.vgaDispBMPControl = new VgaDisplayBMPController(this.mips, vgaDisplay);
    this.menuController = new MenuController(open, exit, go, pause, stepForward);
  }

  @FXML
  public void handleExit() {}

  @FXML
  public void handleGo() {}

  @FXML
  public void handlePause() {}

  @FXML
  public void handleStepForward() {}

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

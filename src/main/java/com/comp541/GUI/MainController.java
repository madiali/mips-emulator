package com.comp541.GUI;

import com.comp541.mips.Mips;
import com.comp541.mips.ProgramLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * The FXML is tied to this Controller and only this controller. All handler methods tied to FXML
 * components must go in this file.
 *
 * <p>Implement Initializable to call initialize() method - user is forced to load a JSON at
 * startup. mips won't be null at startup. This is a bit lazy but prevents us from having to worry
 * about edge cases related to a JSON not being open.
 */
public class MainController implements Initializable {
    private Mips mips;
    private static AccelerometerController accelControl;
    private static VgaDisplayBMPController vgaDisplayBMPController;
    private static RegistersController registersController;
    private static InstructionMemoryController instructionMemoryController;
    private static DataMemoryController dataMemoryController;
    private static OtherMemoryController otherMemoryController;
    private static SoundController soundController;
    private static LedController ledController;
    private static KeyboardController keyboardController;
    private static boolean isExecuting;
    private static Thread execution;

    // @FXML tags are necessary for the variables to be automatically linked to FXML components.
    // Accelerometer
    @FXML
    private Slider xSlider;
    @FXML
    private Slider ySlider;
    @FXML
    private Label xLabel;
    @FXML
    private Label yLabel;
    @FXML
    private Button resetButton;

    // Screen
    @FXML
    private GridPane vgaDisplay;

    // LED
    @FXML
    private Circle led0, led1, led2, led3, led4, led5, led6, led7, led8, led9, led10, led11, led12, led13, led14, led15;

    // Array to store all the LED circles
    private Circle[] leds;

    // Debugging tabs
    @FXML
    private TableView registersTable;
    @FXML
    private TableView instructionMemoryTable;
    @FXML
    private TableView dataMemoryTable;
    @FXML
    private Tab instructionMemoryTab;
    @FXML
    private Tab registersTab;
    @FXML
    private Tab dataMemoryTab;
    @FXML
    private Tab otherMemoryTab;
    @FXML
    private TabPane otherMemoryTabPane;

    // Misc
    @FXML
    private Label statusLabel;

    public static final Path EXAMPLE_PROJECT_DIR = Paths.get("src/test/ExampleProjects");

    /**
     * Constructor with no params is necessary for loader.getController() in AppLauncher.
     */
    public MainController() {
    }

    public String getName() {
        return mips.getName();
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
        // showOpenDialog expects a Stage as its argument but seems to work fine when passed null
        // Probably used when there are multiple windows? Unnecessary here, though
        File selectedFile = fc.showOpenDialog(null);

        // Reset execution state, if user presses Open multiple times
        isExecuting = false;
        statusLabel.setText("Program hasn't started. Press Go to start.");
        if (!otherMemoryTabPane.getTabs().isEmpty()) {
            otherMemoryTabPane.getTabs().clear();
        }

        // Model instantiation
        ProgramLoader pl = new ProgramLoader(selectedFile);
        this.mips = pl.getMips();

        // Controller instantiation
        accelControl = new AccelerometerController(mips, xSlider, ySlider, xLabel, yLabel, resetButton);
        vgaDisplayBMPController = new VgaDisplayBMPController(mips, vgaDisplay);
        registersController = new RegistersController(mips, registersTable);
        instructionMemoryController = new InstructionMemoryController(mips, instructionMemoryTable);
        dataMemoryController = new DataMemoryController(mips, dataMemoryTable);
        otherMemoryController = new OtherMemoryController(mips, otherMemoryTabPane);
        ledController = new LedController(mips, leds);
        keyboardController = new KeyboardController(mips);
    }

    /*
     * Menu handlers. No need for a MenuController
     */

    @FXML
    public void handleExit() {
        System.exit(0);
    }

    @FXML
    public void handleGo() {
        if (!isExecuting) {
            isExecuting = true;
            disableTabs();
            ExecuteAllThrottled ea = new ExecuteAllThrottled(mips, 12.5);
            execution = new Thread(ea);
            execution.start();
            statusLabel.setText("Program is running.");
        }
    }

    /**
     * Set isExecuting = false to pause execution in ExecuteAll to print out time statistics.
     * renderVGA() may be unnecessary since ExecuteAll would've done it, but since program is paused,
     * time doesn't matter.
     */
    @FXML
    public void handlePause() throws InterruptedException {
        isExecuting = false;
        enableTabs();
        // Prevents NullPointerException when user presses Pause before pressing Go, which creates the
        // Thread.
        if (execution != null) {
            execution.join();
        }
        renderAllDisplays();
        statusLabel.setText("Program is paused. Press Step to step forward. View instruction at current PC value in Instruction Memory tab.");
    }

    /**
     * Refreshes everything since program is now paused and speed (probably) doesn't matter.
     */
    @FXML
    public void handleStepForward() throws InterruptedException {
        if (isExecuting) {
            isExecuting = false;
            enableTabs();
            execution.join();
            statusLabel.setText("Program is paused. Press Step to step forward. View instruction at current PC value in Instruction Memory tab.");
        }
        mips.executeNext();
        renderAllDisplays();
    }

    /*
     * Accelerometer
     */

    @FXML
    public void handleXSliderDrag() {
        accelControl.handleXSliderChange();
    }

    @FXML
    public void handleYSliderDrag() {
        accelControl.handleYSliderChange();
    }

    @FXML
    public void handleXSliderClick() {
        accelControl.handleXSliderChange();
    }

    @FXML
    public void handleYSliderClick() {
        accelControl.handleYSliderChange();
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

    /*
     * Execution stuff
     */

    public static boolean getIsExecuting() {
        return isExecuting;
    }

    public static void setIsExecuting(boolean value) {
        isExecuting = value;
    }

    private void disableTabs() {
        instructionMemoryTab.setDisable(true);
        registersTab.setDisable(true);
        dataMemoryTab.setDisable(true);
        otherMemoryTab.setDisable(true);
    }

    private void enableTabs() {
        instructionMemoryTab.setDisable(false);
        registersTab.setDisable(false);
        dataMemoryTab.setDisable(false);
        otherMemoryTab.setDisable(false);
    }

    /**
     * Warning: this will slow the program to an unusable speed if called in ExecuteAll. Use only when
     * stepping forward.
     */
    public void renderAllDisplays() {
        InstructionMemoryController.renderInstructionMemoryTable();
        VgaDisplayBMPController.renderVGA();
        RegistersController.renderRegisterTable();
        DataMemoryController.renderDataMemoryTable();
        otherMemoryController.renderAllTables();
        LedController.renderLED();
    }

    /**
     * This method is called at startup, so user is prompted for JSON at startup. This prevents us
     * from having to worry about edge cases that happen when a JSON is not loaded.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leds = new Circle[]{led0, led1, led2, led3, led4, led5, led6, led7, led8, led9, led10, led11, led12, led13, led14, led15};

        System.out.println(
                "Load a project configuration JSON file. Examples: https://github.com/madiali/mips-emulator/tree/main/" + EXAMPLE_PROJECT_DIR.toString().replace("\\", "/"));
        try {
            handleOpen();
        } catch (IOException ioe) {
            throw new IllegalArgumentException(
                    "Your JSON file doesn't exist or something else went wrong during initialization");
        } catch (NullPointerException npe) {
            System.out.println("No JSON file selected. Please load a required project configuration JSON file next time you run, " +
                    "and examples are at the link above.\nExiting...");
            System.exit(1);
        }
    }
}

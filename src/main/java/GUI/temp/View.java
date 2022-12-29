package GUI.temp;

import GUI.temp.accelerometer.Accelerometer;
import GUI.temp.display.VgaDisplay;
import GUI.temp.menu.EmulatorMenuBar;
import GUI.temp.tabs.DebugTabs;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class View implements FXComponent {
    private BorderPane layout;

    public View() {
    }

    @Override
    public Parent render() {
        layout = new BorderPane();

        // Menu bar (View top)
        EmulatorMenuBar viewMenu = new EmulatorMenuBar();
        layout.setTop(viewMenu.render());

        // Display and accelerometer (Emulator center)
        BorderPane emulatorCenter = new BorderPane();
        Accelerometer accelerometer = new Accelerometer();
        emulatorCenter.setBottom(accelerometer.render());
        VgaDisplay screen = new VgaDisplay();
        emulatorCenter.setCenter(screen.render());

        // Debug tabs (Emulator right)
        DebugTabs debugTabs = new DebugTabs();

        // Emulator (View center)
        BorderPane emulator = new BorderPane();
        emulator.setCenter(emulatorCenter);
        emulator.setRight(debugTabs.render());
        layout.setCenter(emulator);

        return layout;
    }
}
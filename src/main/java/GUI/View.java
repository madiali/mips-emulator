package GUI;

import GUI.menu.EmulatorMenuBar;
import GUI.tab.ViewTabs;
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

        // Screen and accelerometer (Emulator center)
        BorderPane emulatorCenter = new BorderPane();
        Accelerometer accelerometer = new Accelerometer();
        emulatorCenter.setBottom(accelerometer.render());
        Screen screen = new Screen();
        emulatorCenter.setCenter(screen.render());

        // Debug tabs (Emulator right)
        ViewTabs viewTabs = new ViewTabs();

        // Emulator (View center)
        BorderPane emulator = new BorderPane();
        emulator.setCenter(emulatorCenter);
        emulator.setRight(viewTabs.render());
        layout.setCenter(emulator);

        return layout;
    }
}
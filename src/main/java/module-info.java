module mips.emulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    exports GUI;
    opens GUI to javafx.fxml;
    exports GUI.misc;
    opens GUI.misc to javafx.fxml;
    exports GUI.tabs;
    opens GUI.tabs to javafx.fxml;
    exports GUI.menu;
    opens GUI.menu to javafx.fxml;
    exports GUI.display;
    opens GUI.display to javafx.fxml;
    exports GUI.accelerometer;
    opens GUI.accelerometer to javafx.fxml;

    // Required for project.fxml to be able to use Controller
    opens controller to javafx.fxml;
}
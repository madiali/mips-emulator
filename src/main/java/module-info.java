module mips.emulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    exports GUI;
    opens GUI to javafx.fxml;
    exports GUI.temp.misc;
    opens GUI.temp.misc to javafx.fxml;
    exports GUI.temp.tabs;
    opens GUI.temp.tabs to javafx.fxml;
    exports GUI.temp.menu;
    opens GUI.temp.menu to javafx.fxml;
    exports GUI.temp.display;
    opens GUI.temp.display to javafx.fxml;
    exports GUI.temp.accelerometer;
    opens GUI.temp.accelerometer to javafx.fxml;

    // Required for project.fxml to be able to use Controller
    opens controller to javafx.fxml;
    exports GUI.temp;
    opens GUI.temp to javafx.fxml;
    opens controller.temp to javafx.fxml;
}
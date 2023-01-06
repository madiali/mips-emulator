module mips.emulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    exports GUI;
    opens GUI to javafx.fxml;

    // Required for project.fxml to be able to use Controller
    opens GUI.controller to javafx.fxml;
}
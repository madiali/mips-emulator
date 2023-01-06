module mips.emulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    exports GUI;
    opens GUI to javafx.fxml;
    opens GUI.controller to javafx.fxml;
}
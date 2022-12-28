module mips.emulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    exports GUI;
    opens GUI to javafx.fxml;
    exports GUI.misc;
    opens GUI.misc to javafx.fxml;
    exports GUI.tab;
    opens GUI.tab to javafx.fxml;
    exports GUI.menu;
    opens GUI.menu to javafx.fxml;
}
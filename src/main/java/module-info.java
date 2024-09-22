module mips.emulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;
    exports com.comp541.GUI;
    opens com.comp541.GUI to javafx.fxml;
    exports com.comp541;
    opens com.comp541 to javafx.fxml;
}
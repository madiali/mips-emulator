module mips.emulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens GUI to javafx.fxml;
    exports GUI;
}
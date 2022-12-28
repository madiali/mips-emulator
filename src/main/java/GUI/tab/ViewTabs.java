package GUI.tab;

import GUI.FXComponent;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ViewTabs implements FXComponent {
    private TabPane layout;

    public ViewTabs() {
    }

    @Override
    public Parent render() {
        layout = new TabPane();
        layout.setPrefSize(500, 700);

        // Registers
        RegisterTab rt = new RegisterTab();
        Tab registerTab = rt.render();

        // Instruction Memory
        InstructionMemoryTab imt = new InstructionMemoryTab();
        Tab imemTab = imt.render();

        // Data Memory
        DataMemoryTab dmt = new DataMemoryTab();
        Tab dmemTab = dmt.render();

        // IO
        IOTab iot = new IOTab();
        Tab ioTab = iot.render();

        layout.getTabs().addAll(registerTab, imemTab, dmemTab, ioTab);
        return layout;
    }
}

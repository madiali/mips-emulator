package GUI.tab;

import GUI.FXComponent;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class DebugTabs implements FXComponent {
    public final static int PANE_WIDTH = 500;

    private TabPane layout;

    public DebugTabs() {
    }

    @Override
    public Parent render() {
        layout = new TabPane();
        layout.setPrefWidth(PANE_WIDTH);

        // Registers
        Tab registerTab = (new RegisterTab()).render();

        // Instruction Memory
        Tab imemTab = (new InstructionMemoryTab()).render();

        // Data Memory
        Tab dmemTab = (new DataMemoryTab()).render();

        // IO
        Tab ioTab = (new IOTab()).render();

        layout.getTabs().addAll(registerTab, imemTab, dmemTab, ioTab);

        return layout;
    }
}

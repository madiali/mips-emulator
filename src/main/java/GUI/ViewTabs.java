package GUI;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ViewTabs implements FXComponent {
    private TabPane layout;

    public ViewTabs() {}

    @Override
    public Parent render() {
        layout = new TabPane();
        layout.setPrefSize(500, 700);

        // Registers
        Tab registerTab = new Tab("Registers");
        registerTab.setClosable(false);

        // Instruction Memory
        Tab imemTab = new Tab("Instruction Memory");
        imemTab.setClosable(false);

        // Data Memory
        Tab dmemTab = new Tab("Data Memory");
        dmemTab.setClosable(false);

        layout.getTabs().addAll(registerTab, imemTab, dmemTab);
        return layout;
    }
}

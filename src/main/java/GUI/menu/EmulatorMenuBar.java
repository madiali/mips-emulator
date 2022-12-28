package GUI.menu;

import GUI.FXComponent;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;

public class EmulatorMenuBar implements FXComponent {
    private StackPane layout;

    public EmulatorMenuBar() {
    }

    @Override
    public Parent render() {
        layout = new StackPane();
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = (new FileMenu()).render();

        // Run menu
        Menu runMenu = (new RunMenu()).render();

        menuBar.getMenus().addAll(fileMenu, runMenu);
        layout.getChildren().add(menuBar);

        return layout;
    }
}

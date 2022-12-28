/* TODO: add menu bar input control */

package GUI;

import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

public class ViewMenu implements FXComponent {
    private StackPane layout;

    public ViewMenu() {}

    @Override
    public Parent render() {
        layout = new StackPane();
        MenuBar menuBar = new MenuBar();
        Menu tempMenu;
        MenuItem tempMenuItem;

        // File menu
        tempMenu = new Menu("File");
        tempMenuItem = new MenuItem("Open");
        tempMenu.getItems().add(tempMenuItem);
        tempMenuItem = new MenuItem("Exit");
        tempMenu.getItems().add(tempMenuItem);
        menuBar.getMenus().add(tempMenu);

        // Emulator menu
        tempMenu = new Menu("Emulator");
        tempMenuItem = new MenuItem("Run");
        tempMenu.getItems().add(tempMenuItem);
        tempMenuItem = new MenuItem("Pause");
        tempMenu.getItems().add(tempMenuItem);
        tempMenuItem = new MenuItem("Step Forward");
        tempMenu.getItems().add(tempMenuItem);
        menuBar.getMenus().add(tempMenu);

        // Config menu
        tempMenu = new Menu("Config");
        tempMenuItem = new MenuItem("Clock Speed");
        tempMenu.getItems().add(tempMenuItem);
        tempMenuItem = new MenuItem("Sound");
        tempMenu.getItems().add(tempMenuItem);
        menuBar.getMenus().add(tempMenu);

        layout.getChildren().add(menuBar);
        return layout;
    }
}

package GUI.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class FileMenu {
    private Menu fileMenu;
    private MenuItem openMenuItem;
    private MenuItem exitMenuItem;

    public FileMenu() {
        fileMenu = new Menu("File");
        openMenuItem = new MenuItem("Open");
        exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(openMenuItem, exitMenuItem);
    }

    public Menu render() {
        return fileMenu;
    }
}

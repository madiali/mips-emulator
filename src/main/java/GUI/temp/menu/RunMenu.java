package GUI.temp.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RunMenu {
    private Menu runMenu;
    private MenuItem goMenuItem;
    private MenuItem pauseMenuItem;
    private MenuItem stepMenuItem;

    public RunMenu() {
        runMenu = new Menu("Run");
        goMenuItem = new MenuItem("Go");
        pauseMenuItem = new MenuItem("Pause");
        stepMenuItem = new MenuItem("Step Forward");
        runMenu.getItems().addAll(goMenuItem, pauseMenuItem, stepMenuItem);
    }

    public Menu render() {
        return runMenu;
    }
}

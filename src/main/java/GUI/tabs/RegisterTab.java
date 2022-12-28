package GUI.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RegisterTab {
    public final static int NUM_OF_COL = 2;
    public final static int COL_WIDTH = DebugTabs.PANE_WIDTH / NUM_OF_COL;

    private Tab regTab;
    private TableView regTable;
    private TableColumn regNameCol;
    private TableColumn regValCol;

    RegisterTab() {
        regTab = new Tab("Registers");
        regTab.setClosable(false);
        regTable = new TableView();
        regNameCol = new TableColumn("Name");
        regNameCol.setPrefWidth(COL_WIDTH);
        regValCol = new TableColumn("Value");
        regValCol.setPrefWidth(COL_WIDTH);
        regTable.getColumns().addAll(regNameCol, regValCol);
        regTab.setContent(regTable);
    }

    public Tab render() {
        return regTab;
    }
}

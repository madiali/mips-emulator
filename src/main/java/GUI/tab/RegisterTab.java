package GUI.tab;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RegisterTab {
    private Tab regTab;
    private TableView regTable;
    private TableColumn regNameCol;
    private TableColumn regValCol;

    RegisterTab() {
        regTab = new Tab("Registers");
        regTab.setClosable(false);
        regTable = new TableView();
        regNameCol = new TableColumn("Name");
        regNameCol.setPrefWidth(500/2);
        regValCol = new TableColumn("Value");
        regValCol.setPrefWidth(500/2);
        regTable.getColumns().addAll(regNameCol, regValCol);
        regTab.setContent(regTable);
    }

    public Tab render() {
        return regTab;
    }
}

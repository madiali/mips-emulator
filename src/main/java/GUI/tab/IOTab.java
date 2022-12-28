package GUI.tab;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IOTab {
    private Tab ioTab;
    private TableView ioTable;
    private TableColumn ioDeviceCol;
    private TableColumn ioValCol;

    public IOTab() {
        ioTab = new Tab("IO");
        ioTab.setClosable(false);
        ioTable = new TableView();
        ioDeviceCol = new TableColumn("IO Device");
        ioDeviceCol.setPrefWidth(500 / 2);
        ioValCol = new TableColumn("Value");
        ioValCol.setPrefWidth(500 / 2);
        ioTable.getColumns().addAll(ioDeviceCol, ioValCol);
        ioTab.setContent(ioTable);
    }

    public Tab render() {
        return ioTab;
    }
}

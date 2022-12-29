package GUI.temp.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IOTab {
    public final static int NUM_OF_COL = 2;
    public final static int COL_WIDTH = DebugTabs.PANE_WIDTH / NUM_OF_COL;

    private Tab ioTab;
    private TableView ioTable;
    private TableColumn ioDeviceCol;
    private TableColumn ioValCol;

    public IOTab() {
        ioTab = new Tab("IO");
        ioTab.setClosable(false);
        ioTable = new TableView();
        ioDeviceCol = new TableColumn("IO Device");
        ioDeviceCol.setPrefWidth(COL_WIDTH);
        ioValCol = new TableColumn("Value");
        ioValCol.setPrefWidth(COL_WIDTH);
        ioTable.getColumns().addAll(ioDeviceCol, ioValCol);
        ioTab.setContent(ioTable);
    }

    public Tab render() {
        return ioTab;
    }
}

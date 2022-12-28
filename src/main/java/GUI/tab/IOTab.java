package GUI.tab;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IOTab {
    private Tab ioTab;
    private TableView ioTable;
    private TableColumn ioMappedAddrCol;
    private TableColumn ioRelAddrCol;
    private TableColumn ioValCol;

    public IOTab() {
        ioTab = new Tab("IO");
        ioTab.setClosable(false);
        ioTable = new TableView();
        ioMappedAddrCol = new TableColumn("Mapped Address");
        ioMappedAddrCol.setPrefWidth(500/3);
        ioRelAddrCol = new TableColumn("Relative Address");
        ioRelAddrCol.setPrefWidth(500/3);
        ioValCol = new TableColumn("Value");
        ioValCol.setPrefWidth(500/3);
        ioTable.getColumns().addAll(ioMappedAddrCol, ioRelAddrCol, ioValCol);
        ioTab.setContent(ioTable);
    }

    public Tab render() {
        return ioTab;
    }
}

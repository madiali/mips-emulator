package GUI.temp.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DataMemoryTab {
    public final static int NUM_OF_COL = 3;
    public final static int COL_WIDTH = DebugTabs.PANE_WIDTH / NUM_OF_COL;

    private Tab dmemTab;
    private TableView dmemTable;
    private TableColumn dmemMappedAddrCol;
    private TableColumn dmemRelAddrCol;
    private TableColumn dmemValCol;

    public DataMemoryTab() {
        dmemTab = new Tab("Data Memory");
        dmemTab.setClosable(false);
        dmemTable = new TableView();
        dmemMappedAddrCol = new TableColumn("Mapped Address");
        dmemMappedAddrCol.setPrefWidth(COL_WIDTH);
        dmemRelAddrCol = new TableColumn("Relative Address");
        dmemRelAddrCol.setPrefWidth(COL_WIDTH);
        dmemValCol = new TableColumn("Value");
        dmemValCol.setPrefWidth(COL_WIDTH);
        dmemTable.getColumns().addAll(dmemMappedAddrCol, dmemRelAddrCol, dmemValCol);
        dmemTab.setContent(dmemTable);
    }

    public Tab render() {
        return dmemTab;
    }
}

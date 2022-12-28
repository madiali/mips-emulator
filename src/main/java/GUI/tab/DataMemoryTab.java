package GUI.tab;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DataMemoryTab {
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
        dmemMappedAddrCol.setPrefWidth(500 / 3);
        dmemRelAddrCol = new TableColumn("Relative Address");
        dmemRelAddrCol.setPrefWidth(500 / 3);
        dmemValCol = new TableColumn("Value");
        dmemValCol.setPrefWidth(500 / 3);
        dmemTable.getColumns().addAll(dmemMappedAddrCol, dmemRelAddrCol, dmemValCol);
        dmemTab.setContent(dmemTable);
    }

    public Tab render() {
        return dmemTab;
    }
}

package GUI.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InstructionMemoryTab {
    public final static int NUM_OF_COL = 2;
    public final static int COL_WIDTH = DebugTabs.PANE_WIDTH / NUM_OF_COL;

    private Tab imemTab;
    private TableView imemTable;
    private TableColumn imemAddrCol;
    private TableColumn imemInstrCol;

    public InstructionMemoryTab() {
        imemTab = new Tab("Instruction Memory");
        imemTab.setClosable(false);
        imemTable = new TableView();
        imemAddrCol = new TableColumn("Address");
        imemAddrCol.setPrefWidth(COL_WIDTH);
        imemInstrCol = new TableColumn("Instruction");
        imemInstrCol.setPrefWidth(COL_WIDTH);
        imemTable.getColumns().addAll(imemAddrCol, imemInstrCol);
        imemTab.setContent(imemTable);
    }

    public Tab render() {
        return imemTab;
    }
}

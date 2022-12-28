package GUI.tab;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InstructionMemoryTab {
    private Tab imemTab;
    private TableView imemTable;
    private TableColumn imemAddrCol;
    private TableColumn imemInstrCol;

    public InstructionMemoryTab() {
        imemTab = new Tab("Instruction Memory");
        imemTab.setClosable(false);
        imemTable = new TableView();
        imemAddrCol = new TableColumn("Address");
        imemAddrCol.setPrefWidth(500/2);
        imemInstrCol = new TableColumn("Instruction");
        imemInstrCol.setPrefWidth(500/2);
        imemTable.getColumns().addAll(imemAddrCol, imemInstrCol);
        imemTab.setContent(imemTable);
    }

    public Tab render() {
        return imemTab;
    }
}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import mips.DataMemory;
import mips.Mips;

import java.util.HashMap;
import java.util.Map;

public class DataMemoryController {
    private final static double RELATIVE_ADDRESS_COLUMN_WIDTH = 200;
    private final static double VALUE_COLUMN_WIDTH = 300;
    private final static int WORD_SIZE = 4;

    private int[] dmem;
    private TableView dmemTable;
    private TableColumn<Map, String> relativeAddressColumn;
    private TableColumn<Map, String> valueColumn;

    public DataMemoryController(Mips mips, TableView dmemTable) {
        dmem = ((DataMemory) mips.memDict.get(DataMemory.class).get(0)).getMemoryClone();
        this.dmemTable = dmemTable;
        renderDataMemoryTable();
    }

    private void renderDataMemoryTable() {
        relativeAddressColumn = new TableColumn<>("Relative Address");
        relativeAddressColumn.setCellValueFactory(new MapValueFactory<>("relativeAddress"));
        relativeAddressColumn.setPrefWidth(RELATIVE_ADDRESS_COLUMN_WIDTH);
        valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new MapValueFactory<>("value"));
        valueColumn.setPrefWidth(VALUE_COLUMN_WIDTH);
        dmemTable.getColumns().addAll(relativeAddressColumn, valueColumn);
        ObservableList<Map<String, Object>> dmemTableItems = FXCollections.<Map<String, Object>>observableArrayList();
        generateTableCells(dmemTableItems);
        dmemTable.getItems().addAll(dmemTableItems);
    }

    private void generateTableCells(ObservableList<Map<String, Object>> dmemTableItems) {
        for (int relativeAddress = 0; relativeAddress < (dmem.length * WORD_SIZE); relativeAddress += 4) {
            Map<String, Object> item = new HashMap<>();
            item.put("relativeAddress", String.format("0x%08X", relativeAddress));
            item.put("value", String.format("0x%08X", dmem[relativeAddress / WORD_SIZE]));
            dmemTableItems.add(item);
        }
    }
}

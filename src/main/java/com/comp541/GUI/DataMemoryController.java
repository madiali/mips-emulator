package com.comp541.GUI;

import com.comp541.mips.Mips;
import com.comp541.mips.memory.DataMemory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.util.HashMap;
import java.util.Map;

public class DataMemoryController {
    private static final double RELATIVE_ADDRESS_COLUMN_WIDTH = 200;
    private static final double VALUE_COLUMN_WIDTH = 300;
    private static final int WORD_SIZE = 4;

    private static DataMemory dmem;
    private static TableView dmemTable;
    private static ObservableList<Map<String, Object>> dmemTableItems =
            FXCollections.<Map<String, Object>>observableArrayList();

    public DataMemoryController(Mips mips, TableView dmemTable) {
        dmem = (DataMemory) mips.memDict.get(DataMemory.class).get(0);
        DataMemoryController.dmemTable = dmemTable;
        initializeDataMemoryTable();
    }

    private void initializeDataMemoryTable() {
        TableColumn relativeAddressColumn = new TableColumn<>("Relative Address");
        relativeAddressColumn.setCellValueFactory(new MapValueFactory<>("relativeAddress"));
        relativeAddressColumn.setPrefWidth(RELATIVE_ADDRESS_COLUMN_WIDTH);
        TableColumn valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new MapValueFactory<>("value"));
        valueColumn.setPrefWidth(VALUE_COLUMN_WIDTH);
        dmemTable.getColumns().addAll(relativeAddressColumn, valueColumn);
        generateTableCells(dmemTableItems);
        dmemTable.getItems().addAll(dmemTableItems);
    }

    private void generateTableCells(ObservableList<Map<String, Object>> dmemTableItems) {
        for (int relativeAddress = 0; relativeAddress < dmem.getSize(); relativeAddress += 4) {
            Map<String, Object> item = new HashMap<>();
            item.put("relativeAddress", String.format("0x%08X", relativeAddress));
            item.put("value", String.format("0x%08X", dmem.getMemoryUnit(relativeAddress)));
            dmemTableItems.add(item);
        }
    }

    public static void renderDataMemoryTable() {
        for (int relativeAddress = 0; relativeAddress < dmem.getSize(); relativeAddress += 4) {
            Map<String, Object> item = dmemTableItems.get(relativeAddress / WORD_SIZE);
            item.replace("value", String.format("0x%08X", dmem.getMemoryUnit(relativeAddress)));
        }
        dmemTable.refresh();
    }
}

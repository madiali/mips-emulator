package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
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

    private DataMemory dmem;
    private TableView dmemTable;
    private ObservableList<Map<String, Object>> dmemTableItems = FXCollections.<Map<String, Object>>observableArrayList();

    public DataMemoryController(Mips mips, TableView dmemTable) {
        dmem = (DataMemory) mips.memDict.get(DataMemory.class).get(0);
        this.dmemTable = dmemTable;
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

    public void renderDataMemoryTable() {
        for (int relativeAddress = 0; relativeAddress < dmem.getSize(); relativeAddress += 4) {
            Map<String,Object> item = dmemTableItems.get(relativeAddress / WORD_SIZE);
            item.replace("value", String.format("0x%08X", dmem.getMemoryUnit(relativeAddress)));
        }
        dmemTable.refresh();
    }
}

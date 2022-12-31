package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import mips.Mips;
import mips.Registers;

import java.util.HashMap;
import java.util.Map;

public class RegistersController {
    private final static double COLUMN_WIDTH = 250;

    private Registers reg;
    private TableView regTable;
    private TableColumn<Map, String> nameColumn;
    private TableColumn<Map, String> valueColumn;

    public RegistersController(Mips mips, TableView regTable) {
        this.reg = mips.getReg();
        this.regTable = regTable;
        renderRegisterTable();
    }

    public void renderRegisterTable() {
        // Might need to remove previous nameColumn and valueColumn before re-rendering?
        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new MapValueFactory<>("name"));
        nameColumn.setPrefWidth(COLUMN_WIDTH);
        valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new MapValueFactory<>("value"));
        valueColumn.setPrefWidth(COLUMN_WIDTH);
        regTable.getColumns().addAll(nameColumn, valueColumn);
        ObservableList<Map<String, Object>> regTableItems = FXCollections.<Map<String, Object>>observableArrayList();
        generateTableCells(regTableItems);
        regTable.getItems().addAll(regTableItems);
    }

    private void generateTableCells(ObservableList<Map<String, Object>> regTableItems) {
        for (int i = 0; i < 32; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", Registers.registerToName(i));
            item.put("value", String.format("0x%08X", reg.getRegister(i)));
            regTableItems.add(item);
        }
    }
}

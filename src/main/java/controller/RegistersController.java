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
    private final static double NAME_COLUMN_WIDTH = 200;
    private final static double VALUE_COLUMN_WIDTH = 300;

    private static Registers reg;
    private static TableView regTable;
    private static ObservableList<Map<String, Object>> regTableItems = FXCollections.<Map<String, Object>>observableArrayList();

    public RegistersController(Mips mips, TableView regTable) {
        reg = mips.getReg();
        this.regTable = regTable;
        initializeRegisterTable();
    }

    private void initializeRegisterTable() {
        TableColumn nameColumn = new TableColumn<>("Register");
        nameColumn.setCellValueFactory(new MapValueFactory<>("name"));
        nameColumn.setPrefWidth(NAME_COLUMN_WIDTH);
        TableColumn valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new MapValueFactory<>("value"));
        valueColumn.setPrefWidth(VALUE_COLUMN_WIDTH);
        regTable.getColumns().addAll(nameColumn, valueColumn);
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

    /**
     * Renders all rows of register table
     */
    public static void renderRegisterTable() {
        for (int i = 0; i < 32; i++) {
            Map<String,Object> item = regTableItems.get(i);
            item.replace("value", String.format("0x%08X", reg.getRegister(i)));
        }
        regTable.refresh();
    }

    /**
     * Updates single row of register table
     * @param regNum
     */
    public static void renderRegisterTable(int regNum) {
        Map<String,Object> item = regTableItems.get(regNum);
        item.replace("value", String.format("0x%08X", reg.getRegister(regNum)));
        regTable.refresh();
    }
}

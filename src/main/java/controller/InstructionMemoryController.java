package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import mips.InstructionMemory;
import mips.Mips;

import java.util.HashMap;
import java.util.Map;

public class InstructionMemoryController {
    private final static double ADDRESS_COLUMN_WIDTH = 125;
    private final static double INSTRUCTION_COLUMN_WIDTH = 375;

    private InstructionMemory imem;
    private TableView imemTable;
    private TableColumn<Map, String> addressColumn;
    private TableColumn<Map, String> instructionColumn;

    public InstructionMemoryController(Mips mips,TableView imemTable) {
        imem = mips.getInstrMem();
        this.imemTable = imemTable;
        renderInstructionMemoryTable();
    }

    private void renderInstructionMemoryTable() {
        // Might need to remove previous nameColumn and valueColumn before re-rendering?
        addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new MapValueFactory<>("address"));
        addressColumn.setPrefWidth(ADDRESS_COLUMN_WIDTH);
        instructionColumn = new TableColumn<>("Instruction");
        instructionColumn.setCellValueFactory(new MapValueFactory<>("instruction"));
        instructionColumn.setPrefWidth(INSTRUCTION_COLUMN_WIDTH);
        imemTable.getColumns().addAll(addressColumn, instructionColumn);
        ObservableList<Map<String, Object>> imemTableItems = FXCollections.<Map<String, Object>>observableArrayList();
        generateTableCells(imemTableItems);
        imemTable.getItems().addAll(imemTableItems);
    }

    private void generateTableCells(ObservableList<Map<String, Object>> imemTableItems) {
        for (int address = 0; address < imem.getSize(); address += imem.getWordSize()) {
            Map<String, Object> item = new HashMap<>();
            item.put("address", String.format("0x%08X", address));
            item.put("instruction", imem.getInstruction(address).toString());
            imemTableItems.add(item);
        }
    }
}

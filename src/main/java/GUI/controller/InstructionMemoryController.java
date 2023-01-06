/**
 * A cool feature we could add - highlight the instruction that is currently being executed, if
 * possible. May not require a refresh of the whole table?
 */
package GUI.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import mips.Mips;
import mips.memory.InstructionMemory;

import java.util.HashMap;
import java.util.Map;

public class InstructionMemoryController {
  private static final double ADDRESS_COLUMN_WIDTH = 200;
  private static final double INSTRUCTION_COLUMN_WIDTH = 300;

  private static Mips mips;
  private InstructionMemory imem;
  private static TableView imemTable;
  private static int wordSizeLog;

  public InstructionMemoryController(Mips mips, TableView imemTable) {
    this.mips = mips;
    imem = mips.getInstrMem();
    this.imemTable = imemTable;
    wordSizeLog = mips.getInstrMem().getWordSizeLog();
    initializeInstructionMemoryTable();
  }

  private void initializeInstructionMemoryTable() {
    TableColumn addressColumn = new TableColumn<>("Relative Address");
    addressColumn.setCellValueFactory(new MapValueFactory<>("address"));
    addressColumn.setPrefWidth(ADDRESS_COLUMN_WIDTH);
    TableColumn instructionColumn = new TableColumn<>("Instruction");
    instructionColumn.setCellValueFactory(new MapValueFactory<>("instruction"));
    instructionColumn.setPrefWidth(INSTRUCTION_COLUMN_WIDTH);
    imemTable.getColumns().addAll(addressColumn, instructionColumn);
    ObservableList<Map<String, Object>> imemTableItems =
        FXCollections.<Map<String, Object>>observableArrayList();
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

  public static void renderInstructionMemoryTable() {
    int index = (mips.getPC() & 0xFFFF) >> wordSizeLog;
    imemTable.scrollTo(index);
    imemTable.getSelectionModel().select(index);
  }
}

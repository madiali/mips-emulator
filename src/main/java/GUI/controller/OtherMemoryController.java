package GUI.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import mips.memory.MappedMemoryUnit;
import mips.memory.MemoryMapper;
import mips.Mips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherMemoryController {
  private static final double MAPPED_ADDRESS_COLUMN_WIDTH = 200;
  private static final double VALUE_COLUMN_WIDTH = 300;
  // Since dmem is under its own tab, it shouldn't be in OtherMemory.
  private static final String dmemMappedStartAddr = "10010000";

  private TabPane otherMemoryTabPane;
  private List<MappedMemoryUnit> mappedMemoryUnits = new ArrayList<>();
  private List<TableView> tableViewList = new ArrayList<>();
  private List<ObservableList<Map<String, Object>>> memoryItemsList = new ArrayList<>();

  public OtherMemoryController(Mips mips, TabPane otherMemoryTabPane) {
    this.otherMemoryTabPane = otherMemoryTabPane;
    // Get original memory mapper
    MemoryMapper mm = mips.getMemory();
    // Populate new mappedMemoryUnits list without Data Memory since there's already a dmem tab
    for (MappedMemoryUnit mmu : mm.getMemUnits()) {
      // Check for Data Memory by its unique startAddr. Keep other instances of Data Memory
      if (mmu.getStartAddr() != Integer.parseInt(dmemMappedStartAddr, 16)) {
        mappedMemoryUnits.add(mmu);
      }
    }
    initializeTabs();
    initializeTableViews();
  }

  private void initializeTabs() {
    for (MappedMemoryUnit mmu : mappedMemoryUnits) {
      Tab tab = new Tab(mmu.getName());
      TableView tv = new TableView();
      tab.setContent(tv);
      otherMemoryTabPane.getTabs().add(tab);
      tableViewList.add(tv);
      memoryItemsList.add(FXCollections.<Map<String, Object>>observableArrayList());
    }
  }

  private void initializeTableViews() {
    for (int i = 0; i < tableViewList.size(); i++) {
      TableColumn mappedAddressColumn = new TableColumn<>("Mapped Address");
      mappedAddressColumn.setCellValueFactory(new MapValueFactory<>("mappedAddress"));
      mappedAddressColumn.setPrefWidth(MAPPED_ADDRESS_COLUMN_WIDTH);
      TableColumn valueColumn = new TableColumn<>("Value");
      valueColumn.setCellValueFactory(new MapValueFactory<>("value"));
      valueColumn.setPrefWidth(VALUE_COLUMN_WIDTH);

      TableView tv = tableViewList.get(i);
      ObservableList<Map<String, Object>> memoryItems = memoryItemsList.get(i);

      tv.getColumns().addAll(mappedAddressColumn, valueColumn);
      generateTableCells(memoryItems, i);
      tv.getItems().addAll(memoryItems);
    }
  }

  private void generateTableCells(ObservableList<Map<String, Object>> memoryItems, int index) {
    MappedMemoryUnit mmu = mappedMemoryUnits.get(index);
    for (int mappedAddress = mmu.getStartAddr(); mappedAddress < mmu.getEndAddr(); mappedAddress += mmu.getWordSize()) {
      Map<String, Object> item = new HashMap<>();
      item.put("mappedAddress", String.format("0x%08X", mappedAddress));
      item.put("value", String.format("0x%08X", mmu.getMappedMemoryUnit(mappedAddress - mmu.getStartAddr())));
      memoryItems.add(item);
    }
  }

  public void renderAllTables() {
    for (int i = 0; i < mappedMemoryUnits.size(); i++) {
      MappedMemoryUnit mmu = mappedMemoryUnits.get(i);
      for (int relativeAddress = 0; relativeAddress < mmu.getSize(); relativeAddress += mmu.getWordSize()) {
        Map<String,Object> item = memoryItemsList.get(i).get(relativeAddress / mmu.getWordSize());
        item.replace("value", String.format("0x%08X", mmu.getMappedMemoryUnit(relativeAddress)));
      }
      tableViewList.get(i).refresh();
    }
  }
}

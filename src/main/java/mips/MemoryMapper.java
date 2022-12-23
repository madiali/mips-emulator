package mips;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;

public class MemoryMapper implements MemoryUnit {

  private List<MappedMemoryUnit> memUnits;

  public MemoryMapper(List<MappedMemoryUnit> memUnits) {
    this.memUnits = memUnits;
    // Not sure that this line is correct, but it's essentially copied from the original code
    // IntelliJ says this can be replaced by Comparator.comparingInt
    this.memUnits.sort((x, y) -> Integer.compare(x.getStartAddr(), y.getStartAddr()));
  }

  @Deprecated
  public MemoryMapper(int size) {
    int[] data = new int[size];
    DataMemory dataMem = new DataMemory(data);
    MappedMemoryUnit mappedMem = new MappedMemoryUnit(dataMem, 0);
    // List is abstract and can't be instantiated, so I'm using ArrayList
    this.memUnits = new ArrayList<MappedMemoryUnit>();
    // I think the syntax means mappedMem is added to the list
    this.memUnits.add(mappedMem);
  }

  @Override
  public int getMemoryUnit(int address) {
    MappedMemoryUnit unit = findContainingUnit(address);
    return unit.getMappedMemoryUnit(resolveAddress(address, unit));
  }

  @Override
  public void setMemoryUnit(int address, int value) {
    MappedMemoryUnit m = findContainingUnit(address);
    m.setMappedMemoryUnit(resolveAddress(address, m), value);
  }

  @Override
  public int getSize() {
    return memUnits.get(memUnits.size() - 1).getEndAddr() - memUnits.get(0).getStartAddr();
  }

  @Override
  public int getWordSize() {
    return 1;
  }

  public int getStartAddr() {
    return memUnits.get(0).getStartAddr();
  }

  // Should this be static? It's not static in the original code tho
  private MappedMemoryUnit findContainingUnit(int addr) {
    for (int i = 0; i < this.memUnits.size(); i++) {
      MappedMemoryUnit m = this.memUnits.get(i);
      if (m.getStartAddr() <= addr && addr <= m.getEndAddr()) {
        return m;
      }
    }
    throw new UnmappedAddressException(addr);
  }

  // Should this method be static? Not sure how this doesn't cause an error in the getter...
  private int resolveAddress(int addr, MappedMemoryUnit memUnit) {
    return addr - memUnit.getStartAddr();
  }
}

package com.comp541.mips.memory;

import com.comp541.mips.exception.UnmappedAddressException;

import java.util.ArrayList;
import java.util.List;

public class MemoryMapper implements MemoryUnit {

    private List<MappedMemoryUnit> memUnits;

    public MemoryMapper(List<MappedMemoryUnit> memUnits) {
        this.memUnits = memUnits;
        // This sort method mutates memUnits, as expected
        this.memUnits.sort((x, y) -> Integer.compareUnsigned(x.getStartAddr(), y.getStartAddr()));
    }

    // Was marked deprecated, but it's still in use so I removed that
    public MemoryMapper(int size) {
        int[] data = new int[size];
        DataMemory dataMem = new DataMemory(data);
        MappedMemoryUnit mappedMem = new MappedMemoryUnit(dataMem, 0);
        this.memUnits = new ArrayList<>();
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

    private MappedMemoryUnit findContainingUnit(int addr) {
        for (int i = 0; i < this.memUnits.size(); i++) {
            MappedMemoryUnit m = this.memUnits.get(i);
            if (m.getStartAddr() <= addr && addr <= m.getEndAddr()) {
                return m;
            }
        }
        throw new UnmappedAddressException(addr);
    }

    private int resolveAddress(int addr, MappedMemoryUnit memUnit) {
        return addr - memUnit.getStartAddr();
    }

    public List<MappedMemoryUnit> getMemUnits() {
        return memUnits;
    }
}

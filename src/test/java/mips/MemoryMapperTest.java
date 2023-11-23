package mips;

import com.comp541.mips.exception.UnmappedAddressException;
import com.comp541.mips.memory.DataMemory;
import com.comp541.mips.memory.MappedMemoryUnit;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MemoryMapperTest {
    private MemoryMapper target;

    @Before
    public void setup() {
        MappedMemoryUnit mappedMem = buildMappedMemUnit(new int[]{1, 2, 3, 4}, 0);
        MappedMemoryUnit mappedMem2 = buildMappedMemUnit(new int[]{5, 6, 7, 8}, 16);
        MappedMemoryUnit mappedMem3 = buildMappedMemUnit(new int[]{9, 10, 11, 12}, 100);
        List<MappedMemoryUnit> memUnitList = new ArrayList<>();
        memUnitList.add(mappedMem2);
        memUnitList.add(mappedMem3);
        memUnitList.add(mappedMem);
        target = new MemoryMapper(memUnitList);
    }

    @Test
    public void accessMemoryUnitLowerBoundAddressResolved() {
        assertEquals(5, target.getMemoryUnit(16));
    }

    @Test
    public void accessMemoryUnitUpperBoundAddressResolved() {
        assertEquals(8, target.getMemoryUnit(16 + 12));
    }

    @Test
    public void mutateMemoryUnitAddressResolved() {
        target.setMemoryUnit(104, 100000);
        assertEquals(100000, target.getMemoryUnit(104));
    }

    @Test
    public void accessUnmappedAddressThrowsUnmappedAddressException() {
        int i;
        try {
            i = target.getMemoryUnit(48);
            fail();
        } catch (UnmappedAddressException uae) {

        }
    }

    private MappedMemoryUnit buildMappedMemUnit(int[] data, int startAddr) {
        DataMemory dataMem = new DataMemory(data);
        MappedMemoryUnit mappedMem = new MappedMemoryUnit(dataMem, startAddr);
        return mappedMem;
    }
}

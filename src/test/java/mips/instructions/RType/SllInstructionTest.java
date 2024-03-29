package mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.RType.SllInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SllInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private SllInstruction target;

    @Test
    public void executeShiftsInZeroes() {
        reg.setRegister(17, 0x12345678);
        target = new SllInstruction(16, 17, 8);
        target.execute(pc, mem, reg);
        assertEquals(0x34567800, reg.getRegister(16));
        assertEquals(0x4, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new SllInstruction(16, 17, 8);

        assertEquals("SLL $s0, $s1, 8", target.toString());
    }
}

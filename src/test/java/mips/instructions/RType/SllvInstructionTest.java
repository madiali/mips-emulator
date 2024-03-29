package mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.RType.SllvInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SllvInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private SllvInstruction target;

    @Test
    public void executeShiftsInZeroes() {
        reg.setRegister(17, 0x12345678);
        reg.setRegister(18, 0x8);
        target = new SllvInstruction(16, 17, 18);
        target.execute(pc, mem, reg);
        assertEquals(0x34567800, reg.getRegister(16));
        assertEquals(0x4, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new SllvInstruction(16, 17, 18);

        assertEquals("SLLV $s0, $s1, $s2", target.toString());
    }
}

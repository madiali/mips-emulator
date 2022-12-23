package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;
import static org.junit.Assert.*;

public class SrlInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private SrlInstruction target;

    @Test
    public void executeShiftsInZeroes() {
        reg.setRegister(17, 0x82345678);
        target = new SrlInstruction(16, 17, 8);
        target.execute(pc, mem, reg);
        assertEquals(0x00823456, reg.getRegister(16));
        assertEquals(0x4, pc.getPC());
    }
}
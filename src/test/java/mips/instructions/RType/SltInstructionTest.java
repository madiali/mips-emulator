package mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.RType.SltInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SltInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private SltInstruction target;

    @Test
    public void executeIsLessThan() {
        reg.setRegister(17, 0x0);
        reg.setRegister(18, 0x1);
        target = new SltInstruction(16, 17, 18);
        target.execute(pc, mem, reg);
        assertEquals(0x1, reg.getRegister(16));
        assertEquals(0x4, pc.getPC());
    }

    @Test
    public void executeIsLessThanSignedComparison() {
        reg.setRegister(17, 0xFFFFFFFF);
        reg.setRegister(18, 0x00000000);
        target = new SltInstruction(16, 17, 18);
        target.execute(pc, mem, reg);
        assertEquals(0x1, reg.getRegister(16));
        assertEquals(0x4, pc.getPC());
    }

    @Test
    public void executeIsNotLessThan() {
        reg.setRegister(17, 0x1);
        reg.setRegister(18, 0x0);
        target = new SltInstruction(16, 17, 18);
        target.execute(pc, mem, reg);
        assertEquals(0x0, reg.getRegister(16));
        assertEquals(0x4, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new SltInstruction(16, 17, 18);

        assertEquals("SLT $s0, $s1, $s2", target.toString());
    }
}

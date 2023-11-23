package mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.LwInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LwInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private LwInstruction target;

    @Test
    public void executeDataLoadedToReg() {
        mem.setMemoryUnit(4, 0xDEADBEEF);
        target = new LwInstruction(8, 9, 0x0004);
        target.execute(pc, mem, reg);
        assertEquals(0xDEADBEEF, reg.getRegister(8));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void executeDataLoadedToRegSignExtendedImmediate() {
        mem.setMemoryUnit(4, 0xDEADBEEF);
        reg.setRegister(9, 0x00000008);
        target = new LwInstruction(8, 9, 0xFFFC);
        target.execute(pc, mem, reg);
        assertEquals(0xDEADBEEF, reg.getRegister(8));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new LwInstruction(8, 9, 0x0004);
        assertEquals("LW $t0, 0x0004($t1)", target.toString());
    }
}

package mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.BeqInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BeqInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private BeqInstruction target;

    @Test
    public void executeNoBranch() {
        reg.setRegister(1, 1);
        target = new BeqInstruction(1, 0, 0xFFFF);
        target.execute(pc, mem, reg);
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void executeWithBranch() {
        reg.setRegister(1, 0);
        target = new BeqInstruction(1, 0, 0x0002);
        target.execute(pc, mem, reg);
        assertEquals(0x0000000C, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new BeqInstruction(5, 6, 0x1234);

        assertEquals("BEQ $a1, $a2, 0x1234", target.toString());
    }
}

package mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.OriInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OriInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private OriInstruction target;

    @Test
    public void execute() {
        reg.setRegister(8, 0xFFFF0000);
        target = new OriInstruction(9, 8, 0xFFFF);
        target.execute(pc, mem, reg);
        assertEquals(0xFFFFFFFF, reg.getRegister(9));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new OriInstruction(9, 8, 0xFFFF);

        assertEquals("ORI $t1, $t0, 0xFFFF", target.toString());
    }
}

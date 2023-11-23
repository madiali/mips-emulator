package mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.SltiInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SltiInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private SltiInstruction target;

    @Test
    public void executeLessThanImmediate() {
        reg.setRegister(8, 0x00000000);
        target = new SltiInstruction(9, 8, 0x0001);
        target.execute(pc, mem, reg);
        assertEquals(0x00000001, reg.getRegister(9));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void executeLessThanImmediateSignedComparison() {
        reg.setRegister(8, 0xFFFFFFFF);
        target = new SltiInstruction(9, 8, 0x0000);
        target.execute(pc, mem, reg);
        // Not sure why there's trailing 0's here
        assertEquals(0x00000001, reg.getRegister(9));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void executeNotLessThanImmediate() {
        reg.setRegister(8, 0x00000001);
        target = new SltiInstruction(9, 8, 0x0000);
        target.execute(pc, mem, reg);
        assertEquals(0x00000000, reg.getRegister(9));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new SltiInstruction(9, 8, 0x0000);

        assertEquals("SLTI $t1, $t0, 0x0000", target.toString());
    }
}

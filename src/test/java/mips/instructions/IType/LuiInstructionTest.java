package mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.LuiInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LuiInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private LuiInstruction target;

    @Test
    public void executeImmediateInUpperBits() {
        target = new LuiInstruction(8, 0xABCD);
        target.execute(pc, mem, reg);
        assertEquals(0xABCD0000, reg.getRegister(8));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new LuiInstruction(8, 0xABCD);

        assertEquals("LUI $t0, 0xABCD", target.toString());
    }
}

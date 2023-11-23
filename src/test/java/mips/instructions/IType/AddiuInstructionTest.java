package mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.AddiuInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddiuInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private AddiuInstruction target;

    @Test
    public void executeAddPositiveNumbers() {
        reg.setRegister(1, 0x00000002);
        target = new AddiuInstruction(1, 1, 0x0001);
        target.execute(pc, mem, reg);
        assertEquals(0x00000003, reg.getRegister(1));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void executeNegativeImmediate() {
        reg.setRegister(1, 0x00000003);
        target = new AddiuInstruction(2, 1, 0xFFFF);
        target.execute(pc, mem, reg);
        assertEquals(0x00000002, reg.getRegister(2));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void executeOverflow() {
        reg.setRegister(1, 0xFFFFFFFF);
        target = new AddiuInstruction(1, 1, 0x0001);
        target.execute(pc, mem, reg);
        assertEquals(0x00000000, reg.getRegister(1));
        assertEquals(0x00000004, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new AddiuInstruction(1, 2, 0xFFFF);
        assertEquals("ADDIU $at, $v0, 0xFFFF", target.toString());
    }
}

package mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.RType.AndInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AndInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private AndInstruction target;

    @Test
    public void execute() {
        reg.setRegister(9, 0x0000FFFF);
        reg.setRegister(10, 0x00FFFF00);
        target = new AndInstruction(8, 9, 10);
        target.execute(pc, mem, reg);
        assertEquals(0x0000FF00, reg.getRegister(8));
        assertEquals(0x4, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new AndInstruction(8, 9, 10);

        assertEquals("AND $t0, $t1, $t2", target.toString());
    }
}

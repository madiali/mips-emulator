package mips.instructions.IType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;

import static org.junit.Assert.*;

public class AndiInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private AndiInstruction target;

	@Test
    public void execute() {
        reg.setRegister(8, 0x00FFFF00);
        target = new AndiInstruction(9, 8, 0xFFFF);
        target.execute(pc, mem, reg);
        assertEquals(0x0000FF00, reg.getRegister(9));
        assertEquals(0x00000004, pc.getPC());
    }
}

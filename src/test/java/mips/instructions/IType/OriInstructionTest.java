package mips.instructions.IType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;

import static org.junit.Assert.*;

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
}

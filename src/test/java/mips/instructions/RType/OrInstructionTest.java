package mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.RType.OrInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private OrInstruction target;

    @Test
    public void execute() {
        reg.setRegister(17, 0x00FFFF00);
        reg.setRegister(18, 0x0000FFFF);
        target = new OrInstruction(16, 17, 18);
        target.execute(pc, mem, reg);
        assertEquals(0x00FFFFFF, reg.getRegister(16));
        assertEquals(0x4, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new OrInstruction(16, 17, 18);

        assertEquals("OR $s0, $s1, $s2", target.toString());
    }
}

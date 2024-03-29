package mips.instructions.JType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.JType.JInstruction;
import com.comp541.mips.memory.MemoryMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JInstructionTest {
    private ProgramCounter pc = new ProgramCounter(0x00000000);
    private Registers reg = new Registers();
    private MemoryMapper mem = new MemoryMapper(8);
    private JInstruction target;

    @Test
    public void executePCChangedToAddressTimesFour() {
        target = new JInstruction(0x00000003);
        target.execute(pc, mem, reg);
        assertEquals(0x0000000C, pc.getPC());
    }

    @Test
    public void executeUpperFourPcBitsMaintained() {
        pc.setPC(0x20000000);
        target = new JInstruction(0x00000003);
        target.execute(pc, mem, reg);
        assertEquals(0x2000000C, pc.getPC());
    }

    @Test
    public void toStringFormatted() {
        target = new JInstruction(0x00000003);

        assertEquals("J 0x00000003", target.toString());
    }
}

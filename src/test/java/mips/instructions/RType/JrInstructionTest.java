package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;
import static org.junit.Assert.*;

public class JrInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private JrInstruction target;

  @Test
  public void executePcSetToRegisterValue() {
    reg.setRegister(16, 0xFFFFFFFF);
    target = new JrInstruction(16);
    target.execute(pc, mem, reg);
    assertEquals(0xFFFFFFFF, pc.getPC());
  }

  @Test
  public void toStringFormatted() {
    target = new JrInstruction(16);

    assertEquals("JR $s0", target.toString());
  }
}

package mips.instructions.IType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;

import static org.junit.Assert.*;

public class SwInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private SwInstruction target;

  @Test
  public void executeDataStoredInMem() {
    reg.setRegister(8, 0x11037);
    target = new SwInstruction(8, 9, 0x0004);
    target.execute(pc, mem, reg);
    assertEquals(0x11037, mem.getMemoryUnit(4));
    assertEquals(0x00000004, pc.getPC());
  }

  @Test
  public void toStringFormatted() {
    target = new SwInstruction(8, 9, 0x0004);

    assertEquals("SW $t0, 0x0004($t1)", target.toString());
  }
}

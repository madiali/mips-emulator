package mips.instructions.IType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;

import static org.junit.Assert.*;

public class BneInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private BneInstruction target;

  @Test
  public void executeNoBranch() {
    reg.setRegister(1, 0);
    target = new BneInstruction(1, 0, 0x00000002);
    target.execute(pc, mem, reg);
    assertEquals(0x00000004, pc.getPC());
  }

  @Test
  public void executeWithBranch() {
    reg.setRegister(1, 1);
    target = new BneInstruction(1, 0, 0x00000004);
    target.execute(pc, mem, reg);
    assertEquals(0x00000014, pc.getPC());
  }

  @Test
  public void toStringFormatted() {
    target = new BneInstruction(5, 6, 0x1234);
    assertEquals("BNE $a1, $a2, 0x1234", target.toString());
  }
}

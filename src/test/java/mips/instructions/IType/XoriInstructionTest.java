package mips.instructions.IType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;

import static org.junit.Assert.*;

public class XoriInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private XoriInstruction target;

  @Test
  public void execute() {
    reg.setRegister(8, 0x00FFFF00);
    target = new XoriInstruction(9, 8, 0xFFFF);
    target.execute(pc, mem, reg);
    assertEquals(0x00FF00FF, reg.getRegister(9));
    assertEquals(0x00000004, pc.getPC());
  }

  @Test
  public void toStringFormatted() {
    target = new XoriInstruction(9, 8, 0xFFFF);

    assertEquals("XORI $t1, $t0, 0xFFFF", target.toString());
  }
}

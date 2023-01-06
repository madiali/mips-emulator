package mips.instructions.IType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;

import static org.junit.Assert.*;

public class SltiuInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private SltiuInstruction target;

  @Test
  public void executeLessThanImmediate() {
    reg.setRegister(8, 0x00000000);
    target = new SltiuInstruction(9, 8, 0x0001);
    target.execute(pc, mem, reg);
    assertEquals(0x00000001, reg.getRegister(9));
    assertEquals(0x00000004, pc.getPC());
  }

  @Test
  public void executeLessThanImmediateUnsignedComparison() {
    reg.setRegister(8, 0xFFFFFFFF);
    target = new SltiuInstruction(9, 8, 0x0000);
    target.execute(pc, mem, reg);
    assertEquals(0x00000000, reg.getRegister(9));
    assertEquals(0x00000004, pc.getPC());
  }

  @Test
  public void executeNotLessThanImmediate() {
    reg.setRegister(8, 0x00000001);
    target = new SltiuInstruction(9, 8, 0x0000);
    target.execute(pc, mem, reg);
    assertEquals(0x00000000, reg.getRegister(9));
    assertEquals(0x0000004, pc.getPC());
  }

  @Test
  public void toStringFormatted() {
    target = new SltiuInstruction(9, 8, 0x0000);

    assertEquals("SLTIU $t1, $t0, 0x0000", target.toString());
  }
}

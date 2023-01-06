package mips.instructions.RType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;
import static org.junit.Assert.*;

public class AdduInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private AdduInstruction target;

  @Test
  public void executePositiveNumbers() {
    reg.setRegister(9, 0x1);
    reg.setRegister(10, 0x3);
    target = new AdduInstruction(8, 9, 10);
    target.execute(pc, mem, reg);
    assertEquals(0x4, reg.getRegister(8));
    assertEquals(0x4, pc.getPC());
  }

  @Test
  public void executeNegativeNumber() {
    reg.setRegister(9, 0x3);
    reg.setRegister(10, 0xFFFFFFFF);
    target = new AdduInstruction(8, 9, 10);
    target.execute(pc, mem, reg);
    assertEquals(0x2, reg.getRegister(8));
    assertEquals(0x4, pc.getPC());
  }

  @Test
  public void executeOverflow() {
    reg.setRegister(9, 0xFFFFFFFF);
    reg.setRegister(10, 0x1);
    target = new AdduInstruction(8, 9, 10);
    target.execute(pc, mem, reg);
    assertEquals(0x0, reg.getRegister(8));
    assertEquals(0x4, pc.getPC());
  }

  @Test
  public void toStringFormatted() {
    target = new AdduInstruction(8, 9, 10);

    assertEquals("ADDU $t0, $t1, $t2", target.toString());
  }
}

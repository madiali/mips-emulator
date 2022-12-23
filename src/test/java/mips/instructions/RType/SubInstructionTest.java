package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;
import static org.junit.Assert.*;

public class SubInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private SubInstruction target;

  @Test
  public void executePositiveNumbers() {
    reg.setRegister(17, 0x3);
    reg.setRegister(18, 0x1);
    target = new SubInstruction(16, 17, 18);
    target.execute(pc, mem, reg);
    assertEquals(0x2, reg.getRegister(16));
    assertEquals(0x4, pc.getPC());
  }

  @Test
  public void executeNegativeNumber() {
    reg.setRegister(17, 0x3);
    reg.setRegister(18, 0xFFFFFFFF);
    target = new SubInstruction(16, 17, 18);
    target.execute(pc, mem, reg);
    assertEquals(0x4, reg.getRegister(16));
    assertEquals(0x4, pc.getPC());
  }

  @Test
  public void executeUnderflow() {
    reg.setRegister(17, 0x0);
    reg.setRegister(18, 0x1);
    target = new SubInstruction(16, 17, 18);
    target.execute(pc, mem, reg);
    assertEquals(0xFFFFFFFF, reg.getRegister(16));
    assertEquals(0x4, pc.getPC());
  }
}

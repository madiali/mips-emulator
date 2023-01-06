package mips.instructions.RType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;
import static org.junit.Assert.*;

public class SraInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private SraInstruction target;

  @Test
  public void executeSignBitZero() {
    reg.setRegister(17, 0x12345678);
    target = new SraInstruction(16, 17, 8);
    target.execute(pc, mem, reg);
    assertEquals(0x00123456, reg.getRegister(16));
    assertEquals(0x4, pc.getPC());
  }

  @Test
  public void executeSignBitOne() {
    reg.setRegister(17, 0x82345678);
    target = new SraInstruction(16, 17, 8);
    target.execute(pc, mem, reg);
    assertEquals(0xFF823456, reg.getRegister(16));
    assertEquals(0x4, pc.getPC());
  }

  @Test
  public void toStringFormatted() {
    target = new SraInstruction(16, 17, 8);

    assertEquals("SRA $s0, $s1, 8", target.toString());
  }
}

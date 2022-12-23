package mips.instructions.JType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;
import org.junit.Test;
import static org.junit.Assert.*;

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
}

package mips.instructions.JType;

import com.comp541.mips.instructions.JType.JalInstruction;
import com.comp541.mips.memory.MemoryMapper;
import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import org.junit.Test;
import static org.junit.Assert.*;

public class JalInstructionTest {
  private ProgramCounter pc = new ProgramCounter(0x00000000);
  private Registers reg = new Registers();
  private MemoryMapper mem = new MemoryMapper(8);
  private JalInstruction target;

  @Test
  public void executePCChangedToAddressTimesFour() {
    target = new JalInstruction(0x00000003);
    target.execute(pc, mem, reg);
    assertEquals(0x0000000C, pc.getPC());
    assertEquals(0x00000004, reg.getRegister(31));
  }

  @Test
  public void executeUpperFourPcBitsMaintained() {
    pc.setPC(0x20000000);
    target = new JalInstruction(0x00000003);
    target.execute(pc, mem, reg);
    assertEquals(0x2000000C, pc.getPC());
    assertEquals(0x20000004, reg.getRegister(31));
  }

  @Test
  public void toStringFormatted() {
    target = new JalInstruction(0x00000003);

    assertEquals("JAL 0x00000003", target.toString());
  }
}

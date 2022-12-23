package mips;

import mips.InstructionMemory;
import mips.instructions.Instruction;
import mips.instructions.RType.*;
import mips.instructions.IType.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstructionMemoryTest {
  private InstructionMemory target;

  @Before
  public void setup() {
    Instruction instr1 = new AddInstruction(2, 3, 4);
    Instruction instr2 = new LuiInstruction(5, 0xFFFF);
    Instruction[] instructions = {instr1, instr2};
    target = new InstructionMemory(instructions);
  }

  // Comment from original code
  // I assume this test fails in the original code as well... but idk why it fails either
  // TODO: Figure this out
  @Test
  public void setInstructionValidIndex() {
    target = new InstructionMemory(8);
    AddInstruction instr = new AddInstruction(1, 1, 2);

    target.setMemoryUnit(4, 0x00220820);
    AddInstruction targetInstruction = (AddInstruction) target.getInstruction(4);
    assertEquals(instr.getClass(), targetInstruction.getClass());
    assertEquals(instr.getD(), targetInstruction.getD());
    assertEquals(instr.getS(), targetInstruction.getS());
    assertEquals(instr.getT(), targetInstruction.getT());
  }

  @Test
  public void setInstructionInvalidIndexThrowsArgumentException() {
    try {
      target.setMemoryUnit(3, 0x0);
      fail();
    } catch (IllegalArgumentException iae) {

    }
  }

  @Test
  public void getInstructionInvalidIndexThrowsArgumentException() {
    Instruction instr;
    try {
      instr = target.getInstruction(3);
      fail();
    } catch (IllegalArgumentException iae) {

    }
  }
}

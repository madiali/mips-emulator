package mips;

import mips.instructions.Instruction;
import mips.instructions.RType.*;
import mips.instructions.IType.*;
import mips.instructions.JType.*;
import mips.memory.InstructionFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstructionFactoryTest {
  private InstructionFactory target;

  @Before
  public void setup() {
    target = new InstructionFactory();
  }

  @Test
  public void createInstructionADD() {
    Instruction i = target.createInstruction(0x00000020);
    assertEquals(AddInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSUB() {
    Instruction i = target.createInstruction(0x00000022);
    assertEquals(SubInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionAND() {
    Instruction i = target.createInstruction(0x00000024);
    assertEquals(AndInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionOR() {
    Instruction i = target.createInstruction(0x00000025);
    assertEquals(OrInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionXOR() {
    Instruction i = target.createInstruction(0x00000026);
    assertEquals(XorInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionNOR() {
    Instruction i = target.createInstruction(0x00000027);
    assertEquals(NorInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSLT() {
    Instruction i = target.createInstruction(0x0000002A);
    assertEquals(SltInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSLTU() {
    Instruction i = target.createInstruction(0x0000002B);
    assertEquals(SltuInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSLL() {
    Instruction i = target.createInstruction(0x00000000);
    assertEquals(SllInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSLLV() {
    Instruction i = target.createInstruction(0x00000004);
    assertEquals(SllvInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSRL() {
    Instruction i = target.createInstruction(0x00000002);
    assertEquals(SrlInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSRA() {
    Instruction i = target.createInstruction(0x00000003);
    assertEquals(SraInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionJR() {
    Instruction i = target.createInstruction(0x00000008);
    assertEquals(JrInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionLW() {
    Instruction i = target.createInstruction(0x8C000000);
    assertEquals(LwInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSW() {
    Instruction i = target.createInstruction(0xAC000000);
    assertEquals(SwInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionADDI() {
    Instruction i = target.createInstruction(0x20000000);
    assertEquals(AddiInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionADDIU() {
    Instruction i = target.createInstruction(0x24000000);
    assertEquals(AddiuInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSLTI() {
    Instruction i = target.createInstruction(0x28000000);
    assertEquals(SltiInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionSLTIU() {
    Instruction i = target.createInstruction(0x2C000000);
    assertEquals(SltiuInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionORI() {
    Instruction i = target.createInstruction(0x34000000);
    assertEquals(OriInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionLUI() {
    Instruction i = target.createInstruction(0x3C000000);
    assertEquals(LuiInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionBEQ() {
    Instruction i = target.createInstruction(0x10000000);
    assertEquals(BeqInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionBNE() {
    Instruction i = target.createInstruction(0x14000000);
    assertEquals(BneInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionJ() {
    Instruction i = target.createInstruction(0x08000000);
    assertEquals(JInstruction.class, i.getClass());
  }

  @Test
  public void createInstructionJAL() {
    Instruction i = target.createInstruction(0x0C000000);
    assertEquals(JalInstruction.class, i.getClass());
  }

  @Test
  public void unknownOpcodeThrowsException() {
    try {
      target.createInstruction(0xFFFFFFFF);
      fail();
    } catch (IllegalArgumentException iae) {

    }
  }

  @Test
  public void unknownFuncThrowsException() {
    try {
      target.createInstruction(0x00FFFFFF);
      fail();
    } catch (IllegalArgumentException iae) {

    }
  }

  @Test
  public void validInstructionDoesNotThrow() {
    try {
      target.createInstruction(0x0);
    } catch (Exception e) {
      fail();
    }
  }
}

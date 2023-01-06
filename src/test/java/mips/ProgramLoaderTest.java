package mips;

import mips.instructions.Instruction;
import mips.memory.DataMemory;
import mips.memory.InstructionFactory;
import mips.memory.MappedMemoryUnit;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ProgramLoaderTest {
  private ProgramLoader target;

  @Before
  public void setup() throws IOException {
    File file = new File("src/test/TestProjects/Project1/no_errors.json");
    target = new ProgramLoader(file);
  }

  @Test
  public void pcSetFromFile() {
    Mips mips = target.getMips();
    assertEquals(4, mips.getPC());
  }

  @Test
  public void instructionsInitializedBlanksIgnored() {
    assertEquals(49 * 4, target.getMips().getInstrMem().getSize());
  }

  @Test
  public void instructionsInitializedNoComment() {
    InstructionFactory instrFact = new InstructionFactory();
    Instruction expected = instrFact.createInstruction(0x3c08ffff);
    assertEquals(expected.toString(), target.getMips().getInstrMem().getInstruction(4).toString());
  }

  @Test
  public void instructionsInitializedWithComment() {
    InstructionFactory instrFact = new InstructionFactory();
    Instruction expected = instrFact.createInstruction(0x201d003c);
    assertEquals(expected.toString(), target.getMips().getInstrMem().getInstruction(0).toString());
  }

  @Test
  public void memoryInitializedWithStartAddr() {
    assertEquals(3, target.getMips().getMemory().getMemoryUnit(4));
  }

  @Test
  public void memoryInitializedWithStartAndEndAddr() {
    assertEquals(0xf00, target.getMips().getMemory().getMemoryUnit(400));
  }

  @Test
  public void memoryInitializedWithStartAddrAndSize() {
    assertEquals(0xf00, target.getMips().getMemory().getMemoryUnit(2048));
  }

  /** TODO: need better error message */
  @Test
  public void nonExistentMemoryUnitType() {
    File file = new File("src/test/TestProjects/Project1/nonexistent_memory_type.json");
    try {
      ProgramLoader pl = new ProgramLoader(file);
      fail();
    } catch (Exception e) {

    }
  }

  /** TODO: same here */
  @Test
  public void invalidMemoryUnitType() {
    File file = new File("src/test/TestProjects/Project1/invalid_memory_type.json");
    try {
      ProgramLoader pl = new ProgramLoader(file);
      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void parsesHexAndBinValues() {
    MappedMemoryUnit memUnit = target.getMips().getMemory().getMemUnits().get(4);
    assertTrue(memUnit.getMemUnit() instanceof DataMemory);
    // This passes but the startAddr is negative (but sorted correctly, as if it were unsigned, due
    // to MemoryMapper using Integer.compareUnsigned to sort)
    assertEquals(0xDEADBEEF, memUnit.getStartAddr());
    assertEquals(0b100, memUnit.getSize() / memUnit.getWordSize());
  }
}

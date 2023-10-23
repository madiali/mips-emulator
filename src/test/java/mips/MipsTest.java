package mips;

import com.comp541.mips.Mips;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.Instruction;
import com.comp541.mips.memory.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MipsTest {
  private Mips target;
  private Map<Class, List<MemoryUnit>> memDict;

  @Before
  public void setup() {
    int[] instructions =
        new int[] {
          0x201d0f3c, 0x3c08ffff, 0x3508ffff, 0x2009ffff, 0x1509001b, 0x00084600, 0x3508f000,
              0x00084203, 0x00084102,
          0x340a0003, 0x01495022, 0x01484004, 0x010a582a, 0x010a582b, 0x20080005, 0x2d0b000a,
              0x2d0b0004, 0x2008fffb,
          0x2d0b0005, 0x3c0b1010, 0x356b1010, 0x3c0c0101, 0x218c1010, 0x016c6824, 0x016c6825,
              0x016c6826, 0x016c6827,
          0x8c040004, 0x20840002, 0x2484fffe, 0x0c000021, 0xac020000, 0x08F00020, 0x23bdfff8,
              0xafbf0004, 0xafa40000,
          0x28880002, 0x11000002, 0x00041020, 0x0800002e, 0x2084ffff, 0x0c000021, 0x8fa40000,
              0x00441020, 0x00441020,
          0x2042ffff, 0x8fbf0004, 0x23bd0008, 0x03e00008
        };
    InstructionFactory instrFact = new InstructionFactory();
    Instruction[] instrs = new Instruction[instructions.length];
    for (int i = 0; i < instructions.length; i++) {
      instrs[i] = instrFact.createInstruction(instructions[i]);
    }
    InstructionMemory instrMem = new InstructionMemory(instrs);
    MemoryUnit dataMem = new DataMemory(10000);
    dataMem.setMemoryUnit(4, 99);
    MappedMemoryUnit map = new MappedMemoryUnit(dataMem, 0);
    List<MappedMemoryUnit> unitList = new ArrayList<>();
    unitList.add(map);
    MemoryMapper mem = new MemoryMapper(unitList);
    memDict = new HashMap<>();
    List<MemoryUnit> instrMemList = new ArrayList<>();
    instrMemList.add(instrMem);
    memDict.put(instrMem.getClass(), instrMemList);
    List<MemoryUnit> memList = new ArrayList<>();
    memList.add(mem);
    memDict.put(mem.getClass(), memList);

    // Original code calls with only the first two args, I manually put in the rest as their default
    // values
    target = new Mips(0x0, memDict, null, "", 0);
  }

  @Test
  public void constructNoRegisters() {
    target = new Mips(0x0, memDict, null, "", 0);
    assertNotNull(target.getReg());
  }

  @Test
  public void constructRegisters() {
    Registers reg = new Registers();
    target = new Mips(0x0, memDict, reg, "", 0);
    assertEquals(reg, target.getReg());
  }

  @Test
  public void executeNextPcAndRegModified() {
    target.executeNext();
    assertEquals(0x4, target.getPC());
    assertEquals(0xF3C, target.getReg().getRegister(29));
  }

  @Test
  public void executeAll() {
    target.executeAll();
    assertEquals(99 * 99, target.getMemory().getMemoryUnit(0));
  }
}

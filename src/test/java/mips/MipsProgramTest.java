package mips;

import mips.instructions.Instruction;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class MipsProgramTest {
  private InstructionMemory target;

  @Test
  public void testInitInstructionMemory() {
    int[] instructions =
        new int[] {
          0x201d0f3c, 0x3c08ffff, 0x3508ffff, 0x2009ffff, 0x1509001b, 0x00084600, 0x3508f000,
              0x00084203, 0x00084102,
          0x340a0003, 0x01495022, 0x01484004, 0x010a582a, 0x010a582b, 0x20080005, 0x2d0b000a,
              0x2d0b0004, 0x2008fffb,
          0x2d0b0005, 0x3c0b1010, 0x356b1010, 0x3c0c0101, 0x218c1010, 0x016c6824, 0x016c6825,
              0x016c6826, 0x016c6827,
          0x8c040004, 0x20840002, 0x2484fffe, 0x0c000021, 0xac020000, 0x08000020, 0x23bdfff8,
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
    target = new InstructionMemory(instrs);
    ProgramCounter pc = new ProgramCounter(0);
    DataMemory dataMem = new DataMemory(10000);
    MappedMemoryUnit map = new MappedMemoryUnit(dataMem, 0);
    List<MappedMemoryUnit> unitList = new ArrayList<>();
    unitList.add(map);
    MemoryMapper dataMemory = new MemoryMapper(unitList);
    Registers registers = new Registers();

    int icount = 0;
    int a = 99;
    dataMemory.setMemoryUnit(4, a);

    while (pc.getPC() < instructions.length * 4 && icount < 1000000) {
      target.getInstruction(pc.getPC()).execute(pc, dataMemory, registers);
      icount++;
    }

    System.out.println(dataMemory.getMemoryUnit(0));

    assertEquals(a * a, dataMemory.getMemoryUnit(0));
  }
}

package MIPS_Emulator.Instructions.JType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class JInstruction extends JTypeInstruction {
  public JInstruction(int target) {
    super(target);
    this.name = "J";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    pc.setPC((pc.getPC() & 0xF0000000) | (target << 2));
  }
}

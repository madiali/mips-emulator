package mips.instructions.JType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

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

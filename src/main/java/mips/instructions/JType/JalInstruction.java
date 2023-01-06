package mips.instructions.JType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class JalInstruction extends JTypeInstruction {
  public JalInstruction(int target) {
    super(target);
    this.name = "JAL";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(31, pc.getPC() + 4);
    pc.setPC((pc.getPC() & 0xF0000000) | (target << 2));
  }
}

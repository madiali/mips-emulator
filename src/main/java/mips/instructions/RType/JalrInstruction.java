package mips.instructions.RType;

import mips.ProgramCounter;
import mips.Registers;
import mips.memory.MemoryMapper;

public class JalrInstruction extends RTypeInstruction {
  public JalrInstruction(int s) {
    super(0, s, 0);
    this.name = "JALR";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(31, pc.getPC() + 4);
    pc.setPC(reg.getRegister(s));
  }

  @Override
  public String toString() {
    return name + " " + Registers.registerToName(s);
  }
}

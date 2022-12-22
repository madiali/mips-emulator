package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class JrInstruction extends RTypeInstruction {
  public JrInstruction(int s) {
    super(0, s, 0);
    this.name = "JR";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    pc.setPC(reg.getRegister(s));
  }
}

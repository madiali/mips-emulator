package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class NorInstruction extends RTypeInstruction {
  public NorInstruction(int d, int s, int t) {
    super(d, s, t);
    this.name = "NOR";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(d, ~(reg.getRegister(s) | reg.getRegister(t)));
    pc.incrementPC(4);
  }
}
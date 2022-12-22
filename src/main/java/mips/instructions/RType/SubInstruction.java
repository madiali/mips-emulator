package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class SubInstruction extends RTypeInstruction {
  public SubInstruction(int d, int s, int t) {
    super(d, s, t);
    this.name = "SUB";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(d, reg.getRegister(s) - reg.getRegister(t));
    pc.incrementPC(4);
  }
}
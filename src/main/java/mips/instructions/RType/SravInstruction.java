package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class SravInstruction extends RTypeInstruction {
  public SravInstruction(int d, int t, int s) {
    super(d, s, t);
    this.name = "SRAV";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(d, reg.getRegister(t) >> reg.getRegister(s));
    pc.incrementPC(4);
  }
}

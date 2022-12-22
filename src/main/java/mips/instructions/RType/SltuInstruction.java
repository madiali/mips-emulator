package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class SltuInstruction extends RTypeInstruction {
  public SltuInstruction(int d, int s, int t) {
    super(d, s, t);
    this.name = "SLTU";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    boolean isLessThan = reg.getRegister(s) < reg.getRegister(t);
    reg.setRegister(d, (isLessThan ? 1 : 0));
    pc.incrementPC(4);
  }
}

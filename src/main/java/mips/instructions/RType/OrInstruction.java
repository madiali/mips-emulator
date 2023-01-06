package mips.instructions.RType;

import mips.ProgramCounter;
import mips.Registers;
import mips.memory.MemoryMapper;

public class OrInstruction extends RTypeInstruction {
  public OrInstruction(int d, int s, int t) {
    super(d, s, t);
    this.name = "OR";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(d, reg.getRegister(s) | reg.getRegister(t));
    pc.incrementPC(4);
  }
}

package mips.instructions.IType;

import mips.ProgramCounter;
import mips.Registers;
import mips.memory.MemoryMapper;

public class OriInstruction extends ITypeInstruction {
  public OriInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "ORI";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, reg.getRegister(s) | immediate);
    pc.incrementPC(4);
  }
}

package mips.instructions.IType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class XoriInstruction extends ITypeInstruction {
  public XoriInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "XORI";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, reg.getRegister(s) ^ immediate);
    pc.incrementPC(4);
  }
}

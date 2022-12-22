package mips.instructions.IType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class SltiuInstruction extends ITypeInstruction {
  public SltiuInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "SLTIU";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    boolean isLessThanImmediate = reg.getRegister(s) < signExtend(immediate);
    reg.setRegister(t, (isLessThanImmediate ? 1 : 0));
    pc.incrementPC(4);
  }
}
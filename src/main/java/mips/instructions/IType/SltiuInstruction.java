package mips.instructions.IType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class SltiuInstruction extends ITypeInstruction {
  public SltiuInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "SLTIU";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    int sUpperBit = reg.getRegister(s) >>> 31;
    int immediateUpperBit = signExtend(immediate) >>> 31;
    boolean isLessThanImmediate;
    if (sUpperBit == 1 && immediateUpperBit == 0) {
      isLessThanImmediate = false;
    } else if (sUpperBit == 0 && immediateUpperBit == 1) {
      isLessThanImmediate = true;
    } else {
      isLessThanImmediate = reg.getRegister(s) < signExtend(immediate);
    }
    reg.setRegister(t, (isLessThanImmediate ? 1 : 0));
    pc.incrementPC(4);
  }
}

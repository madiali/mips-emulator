package com.comp541.mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class SltiInstruction extends ITypeInstruction {
  public SltiInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "SLTI";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    boolean isLessThanImmediate = reg.getRegister(s) < signExtend(immediate);
    reg.setRegister(t, (isLessThanImmediate ? 1 : 0));
    pc.incrementPC(4);
  }
}

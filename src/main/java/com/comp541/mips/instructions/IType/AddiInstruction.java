package com.comp541.mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class AddiInstruction extends ITypeInstruction {
  public AddiInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "ADDI";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, reg.getRegister(s) + signExtend(immediate));
    pc.incrementPC(4);
  }
}

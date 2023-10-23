package com.comp541.mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class LwInstruction extends ITypeInstruction {
  public LwInstruction(int t, int s, int offset) {
    super(t, s, offset);
    this.name = "LW";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, mem.getMemoryUnit(reg.getRegister(s) + signExtend(immediate)));
    pc.incrementPC(4);
  }

  @Override
  public String toString() {
    return name
        + " "
        + Registers.registerToName(t)
        + ", "
        + String.format("0x%04X(", immediate)
        + Registers.registerToName(s)
        + ")";
  }
}

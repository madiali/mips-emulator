package com.comp541.mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

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

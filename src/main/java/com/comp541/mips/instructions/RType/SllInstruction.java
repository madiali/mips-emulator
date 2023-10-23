package com.comp541.mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class SllInstruction extends RTypeInstruction {
  private final int shamt;

  public SllInstruction(int d, int t, int shamt) {
    super(d, shamt, t);
    this.name = "SLL";
    this.shamt = shamt;
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    int shamt = s;
    reg.setRegister(d, reg.getRegister(t) << shamt);
    pc.incrementPC(4);
  }

  @Override
  public String toString() {
    return name
        + " "
        + Registers.registerToName(d)
        + ", "
        + Registers.registerToName(t)
        + ", "
        + shamt;
  }
}

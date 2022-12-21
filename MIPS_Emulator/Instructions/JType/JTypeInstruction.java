package MIPS_Emulator.Instructions.JType;

import MIPS_Emulator.Instructions.Instruction;
import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public abstract class JTypeInstruction implements Instruction {
  protected String name = null;
  protected final int target;

  protected JTypeInstruction(int target) {
    this.target = target;
  }

  @Override
  public abstract void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);

  @Override
  public String toString() {
    return name + " " + String.format("0x%04X", target);
  }
}

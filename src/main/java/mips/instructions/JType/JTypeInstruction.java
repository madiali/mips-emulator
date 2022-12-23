package mips.instructions.JType;

import mips.instructions.Instruction;
import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public abstract class JTypeInstruction implements Instruction {
  protected String name;
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

  public String getName() {
    return this.name;
  }

  public int getTarget() {
    return target;
  }
}

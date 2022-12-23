package mips.instructions.RType;

import mips.instructions.Instruction;
import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public abstract class RTypeInstruction implements Instruction {
  protected String name;
  protected final int d, s, t;

  protected RTypeInstruction(int d, int s, int t) {
    this.d = d;
    this.s = s;
    this.t = t;
  }

  @Override
  public abstract void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);

  @Override
  public String toString() {
    return name
        + " "
        + Registers.registerToName(d)
        + ", "
        + Registers.registerToName(s)
        + ", "
        + Registers.registerToName(t);
  }

  public String getName() {
    return this.name;
  }

  public int getD() {
    return d;
  }

  public int getS() {
    return s;
  }

  public int getT() {
    return t;
  }
}

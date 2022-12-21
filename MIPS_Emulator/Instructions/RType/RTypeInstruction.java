package MIPS_Emulator.Instructions.RType;

import MIPS_Emulator.Instructions.Instruction;
import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

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
}

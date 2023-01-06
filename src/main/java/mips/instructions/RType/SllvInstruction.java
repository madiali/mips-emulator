package mips.instructions.RType;

import mips.ProgramCounter;
import mips.Registers;
import mips.memory.MemoryMapper;

public class SllvInstruction extends RTypeInstruction {
  public SllvInstruction(int d, int t, int s) {
    super(d, s, t);
    this.name = "SLLV";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(d, reg.getRegister(t) << reg.getRegister(s));
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
        + Registers.registerToName(s);
  }
}

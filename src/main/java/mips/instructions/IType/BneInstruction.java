package mips.instructions.IType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class BneInstruction extends ITypeInstruction {
  public BneInstruction(int s, int t, int offset) {
    super(t, s, offset);
    this.name = "BNE";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    pc.incrementPC(4);
    if (reg.getRegister(s) != reg.getRegister(t)) {
      pc.incrementPC(immediate << 2);
    }
  }

  @Override
  public String toString() {
    return name
            + " "
            + Registers.registerToName(s)
            + ", "
            + Registers.registerToName(t)
            + ", "
            + String.format("0x%04X", immediate);
  }
}

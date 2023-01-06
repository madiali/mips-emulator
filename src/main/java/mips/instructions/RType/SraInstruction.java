package mips.instructions.RType;

import mips.memory.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class SraInstruction extends RTypeInstruction {
  private final int shamt;

  public SraInstruction(int d, int t, int shamt) {
    super(d, shamt, t);
    this.name = "SRA";
    this.shamt = shamt;
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    int shamt = s;
    reg.setRegister(d, reg.getRegister(t) >> shamt);
    pc.incrementPC(4);
  }

  @Override
  public String toString() {
    return name + " " + Registers.registerToName(d) + ", " + Registers.registerToName(t) + ", " + shamt;
  }
}

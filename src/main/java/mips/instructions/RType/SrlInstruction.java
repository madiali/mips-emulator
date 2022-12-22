package mips.instructions.RType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class SrlInstruction extends RTypeInstruction {
  public SrlInstruction(int d, int t, int shamt) {
    super(d, shamt, t);
    this.name = "SRL";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    int shamt = s;
    reg.setRegister(d, reg.getRegister(t) >>> shamt);
    pc.incrementPC(4);
  }
}

package MIPS_Emulator.Instructions.RType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class SravInstruction extends RTypeInstruction {
  public SravInstruction(int d, int t, int s) {
    super(d, s, t);
    this.name = "SRAV";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(d, reg.getRegister(t) >> reg.getRegister(s));
    pc.incrementPC(4);
  }
}

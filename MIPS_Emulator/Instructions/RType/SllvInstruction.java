package MIPS_Emulator.Instructions.RType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

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
}

package MIPS_Emulator.Instructions.RType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

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

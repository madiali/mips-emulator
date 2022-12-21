package MIPS_Emulator.Instructions.RType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class SraInstruction extends RTypeInstruction {
  public SraInstruction(int d, int t, int shamt) {
    super(d, shamt, t);
    this.name = "SRA";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    int shamt = s;
    reg.setRegister(d, reg.getRegister(t) >> shamt);
    pc.incrementPC(4);
  }
}

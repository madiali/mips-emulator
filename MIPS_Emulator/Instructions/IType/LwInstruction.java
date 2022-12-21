package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class LwInstruction extends ITypeInstruction {
  public LwInstruction(int t, int s, int offset) {
    super(t, s, offset);
    this.name = "LW";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, mem.getMemoryUnit(reg.getRegister(s)) + signExtend(immediate));
    pc.incrementPC(4);
  }
}

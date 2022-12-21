package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class SwInstruction extends ITypeInstruction {
  public SwInstruction(int t, int s, int offset) {
    super(t, s, offset);
    this.name = "SW";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    mem.setMemoryUnit(reg.getRegister(s) + signExtend(immediate), reg.getRegister(t));
    pc.incrementPC(4);
  }
}

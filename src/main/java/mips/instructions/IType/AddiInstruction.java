package mips.instructions.IType;

import mips.ProgramCounter;
import mips.Registers;
import mips.memory.MemoryMapper;

public class AddiInstruction extends ITypeInstruction {
  public AddiInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "ADDI";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, reg.getRegister(s) + signExtend(immediate));
    pc.incrementPC(4);
  }
}

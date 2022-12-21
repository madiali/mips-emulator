package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class XoriInstruction extends ITypeInstruction {
  public XoriInstruction(int t, int s, int immediate) {
    super(t, s, immediate);
    this.name = "XORI";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, reg.getRegister(s) ^ immediate);
    pc.incrementPC(4);
  }
}

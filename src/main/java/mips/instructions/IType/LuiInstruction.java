package mips.instructions.IType;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public class LuiInstruction extends ITypeInstruction {
  public LuiInstruction(int t, int immediate) {
    super(t, 0, immediate);
    this.name = "LUI";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(t, immediate << 16);
    pc.incrementPC(4);
  }
}

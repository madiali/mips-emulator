package MIPS_Emulator.Instructions.RType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class JalrInstruction extends RTypeInstruction {
  public JalrInstruction(int s) {
    super(0, s, 0);
    this.name = "JALR";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(31, pc.getPC() + 4);
    pc.setPC(reg.getRegister(s));
  }
}

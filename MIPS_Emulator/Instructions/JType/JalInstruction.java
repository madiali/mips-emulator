package MIPS_Emulator.Instructions.JType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class JalInstruction extends JTypeInstruction {
  public JalInstruction(int target) {
    super(target);
    this.name = "JAL";
  }

  @Override
  public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
    reg.setRegister(31, pc.getPC() + 4);
    pc.setPC((pc.getPC() & 0xF0000000) | (target << 2));
  }
}

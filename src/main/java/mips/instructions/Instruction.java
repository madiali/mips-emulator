package mips.instructions;

import mips.MemoryMapper;
import mips.ProgramCounter;
import mips.Registers;

public interface Instruction {
  void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);
}

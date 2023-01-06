package mips.instructions;

import mips.ProgramCounter;
import mips.Registers;
import mips.memory.MemoryMapper;

public interface Instruction {
  void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);
}

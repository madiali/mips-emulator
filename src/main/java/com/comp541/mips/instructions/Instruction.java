package com.comp541.mips.instructions;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public interface Instruction {
  void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);
}

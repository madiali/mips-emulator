package MIPS_Emulator.Instructions;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public interface Instruction {
    void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);
}

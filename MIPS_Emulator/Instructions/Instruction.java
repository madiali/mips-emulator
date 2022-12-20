package MIPS_Emulator.Instructions;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.Registers;

public interface Instruction {
    void execute(MemoryMapper mem, Registers reg);
}

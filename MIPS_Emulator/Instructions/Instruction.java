package MIPS_Emulator.Instructions;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.Mips;
import MIPS_Emulator.Registers;

public interface Instruction {
    int execute(int pc, MemoryMapper mem, Registers reg);
}

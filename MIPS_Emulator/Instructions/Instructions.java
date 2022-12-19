package MIPS_Emulator.Instructions;

import MIPS_Emulator.Mips;

public interface Instructions {
    void Execute(MemoryMapper mem, Registers reg);
}

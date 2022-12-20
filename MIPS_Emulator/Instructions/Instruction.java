package MIPS_Emulator.Instructions;

public interface Instruction {
    void Execute(MemoryMapper mem, Registers reg);
}

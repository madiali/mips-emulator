package MIPS_Emulator.Instructions;

public interface Instructions {
    void Execute(MemoryMapper mem, Registers reg);
}

package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class AndiInstruction extends ITypeInstruction {
    public AndiInstruction(int t, int s, int immediate) {
        super(t, s, immediate);
        this.name = "ANDI";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        reg.setRegister(t, s & immediate);
        pc.incrementPC(4);
    }
}

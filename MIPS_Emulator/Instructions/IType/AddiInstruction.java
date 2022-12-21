package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class AddiInstruction extends ITypeInstruction {
    public AddiInstruction(int t, int s, int immediate) {
        super(t, s, immediate);
        this.name = "ADDI";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        reg.setRegister(t, reg.getRegister(s) + signExtend(immediate));
        pc.incrementPC(4);
    }
}

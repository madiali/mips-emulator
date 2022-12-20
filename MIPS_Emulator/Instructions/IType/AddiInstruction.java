package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class AddiInstruction extends ITypeInstruction {
    public AddiInstruction(int t, int s, int immediate) {
        super(t, s, immediate);
        super.name = "ADDI";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        reg.setRegister(super.getT(), reg.getRegister(super.getS()) + signExtend(super.getImmediate()));
        pc.incrementPC(4);
    }
}

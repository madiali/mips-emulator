package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class BneInstruction extends ITypeInstruction {
    public BneInstruction(int s, int t, int offset) {
        super(t, s, offset);
        this.name = "BNE";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        pc.incrementPC(4);
        if (reg.getRegister(s) != reg.getRegister(t)) {
            pc.incrementPC(immediate << 2);
        }
    }
}

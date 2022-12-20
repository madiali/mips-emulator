package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class BeqInstruction extends ITypeInstruction {
    public BeqInstruction(int s, int t, int offset) {
        super(t, s, offset);
        super.name = "BEQ";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        pc.incrementPC(4);
        if (reg.getRegister(s) == reg.getRegister(t)) {
            pc.incrementPC(immediate << 2);
        }
    }
}

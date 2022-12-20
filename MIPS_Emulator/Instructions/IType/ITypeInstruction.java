package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.Instructions.Instruction;
import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public abstract class ITypeInstruction implements Instruction {
    protected String name = null;
    protected final int t, s, immediate;

    protected ITypeInstruction(int t, int s, int immediate) {
        this.t = t;
        this.s = s;
        this.immediate = immediate;
    }

    @Override
    public abstract void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);

    protected static int signExtend(int immediate) {
        int sign = (immediate >> 15) & 0b1;
        immediate = (sign == 0) ? immediate : (immediate | 0xFFFF0000);
        return immediate;
    }

    @Override
    public String toString() {
        return name + " " + Registers.registerToName(t) + ", " + Registers.registerToName(s) + ", " + String.format("0x%04X", immediate);
    }
}

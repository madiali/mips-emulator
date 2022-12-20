package MIPS_Emulator.Instructions.IType;

import MIPS_Emulator.Instructions.Instruction;
import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.Registers;

public abstract class ITypeInstruction implements Instruction {
    protected String name = null;
    private int t, s, immediate;

    protected ITypeInstruction(int t, int s, int immediate) {
        this.t = t;
        this.s = s;
        this.immediate = immediate;
    }

    protected int getT() {
        return t;
    }

    protected int getS() {
        return s;
    }

    protected int getImmediate() {
        return immediate;
    }

    public abstract int execute(int pc, MemoryMapper mem, Registers reg);

    protected static int signExtend(int immediate) {
        int sign = (immediate >> 15) & 0b1;
        immediate = (sign == 0) ? immediate : (immediate | 0xFFFF0000);
        return immediate;
    }

    @Override
    public String toString() {
        return name + " " + Registers.registerToName(t) + ", " + Registers.registerToName(s) + ", " + String.format("0x%16X", immediate);
    }
}

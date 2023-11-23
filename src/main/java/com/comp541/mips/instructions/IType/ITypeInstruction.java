package com.comp541.mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.Instruction;
import com.comp541.mips.memory.MemoryMapper;

public abstract class ITypeInstruction implements Instruction {
    protected String name;
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
        return name
                + " "
                + Registers.registerToName(t)
                + ", "
                + Registers.registerToName(s)
                + ", "
                + String.format("0x%04X", immediate);
    }

    public String getName() {
        return this.name;
    }

    public int getT() {
        return t;
    }

    public int getS() {
        return s;
    }

    public int getImmediate() {
        return immediate;
    }
}

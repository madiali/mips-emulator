package com.comp541.mips.instructions.JType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.Instruction;
import com.comp541.mips.memory.MemoryMapper;

public abstract class JTypeInstruction implements Instruction {
    protected String name;
    protected final int target;

    protected JTypeInstruction(int target) {
        this.target = target;
    }

    @Override
    public abstract void execute(ProgramCounter pc, MemoryMapper mem, Registers reg);

    @Override
    public String toString() {
        return name + " " + String.format("0x%08X", target);
    }

    public String getName() {
        return this.name;
    }

    public int getTarget() {
        return target;
    }
}

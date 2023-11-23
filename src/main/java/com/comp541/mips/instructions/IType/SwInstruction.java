package com.comp541.mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class SwInstruction extends ITypeInstruction {
    public SwInstruction(int t, int s, int offset) {
        super(t, s, offset);
        this.name = "SW";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        mem.setMemoryUnit(reg.getRegister(s) + signExtend(immediate), reg.getRegister(t));
        pc.incrementPC(4);
    }

    @Override
    public String toString() {
        return name
                + " "
                + Registers.registerToName(t)
                + ", "
                + String.format("0x%04X(", immediate)
                + Registers.registerToName(s)
                + ")";
    }
}

package com.comp541.mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class SltInstruction extends RTypeInstruction {
    public SltInstruction(int d, int s, int t) {
        super(d, s, t);
        this.name = "SLT";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        boolean isLessThan = reg.getRegister(s) < reg.getRegister(t);
        reg.setRegister(d, (isLessThan ? 1 : 0));
        pc.incrementPC(4);
    }
}

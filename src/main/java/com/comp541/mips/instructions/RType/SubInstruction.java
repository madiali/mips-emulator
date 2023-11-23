package com.comp541.mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class SubInstruction extends RTypeInstruction {
    public SubInstruction(int d, int s, int t) {
        super(d, s, t);
        this.name = "SUB";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        reg.setRegister(d, reg.getRegister(s) - reg.getRegister(t));
        pc.incrementPC(4);
    }
}

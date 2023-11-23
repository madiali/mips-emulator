package com.comp541.mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class AdduInstruction extends RTypeInstruction {
    public AdduInstruction(int d, int s, int t) {
        super(d, s, t);
        this.name = "ADDU";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        reg.setRegister(d, reg.getRegister(s) + reg.getRegister(t));
        pc.incrementPC(4);
    }
}

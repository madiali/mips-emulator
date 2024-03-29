package com.comp541.mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class SltuInstruction extends RTypeInstruction {
    public SltuInstruction(int d, int s, int t) {
        super(d, s, t);
        this.name = "SLTU";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        int sUpperBit = reg.getRegister(s) >>> 31;
        int rUpperBit = reg.getRegister(t) >>> 31;
        boolean isLessThan;
        if (sUpperBit == 1 && rUpperBit == 0) {
            isLessThan = false;
        } else if (sUpperBit == 0 && rUpperBit == 1) {
            isLessThan = true;
        } else {
            isLessThan = reg.getRegister(s) < reg.getRegister(t);
        }
        reg.setRegister(d, (isLessThan ? 1 : 0));
        pc.incrementPC(4);
    }
}

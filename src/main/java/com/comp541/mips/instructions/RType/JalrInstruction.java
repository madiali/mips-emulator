package com.comp541.mips.instructions.RType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class JalrInstruction extends RTypeInstruction {
    public JalrInstruction(int s) {
        super(0, s, 0);
        this.name = "JALR";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        reg.setRegister(31, pc.getPC() + 4);
        pc.setPC(reg.getRegister(s));
    }

    @Override
    public String toString() {
        return name + " " + Registers.registerToName(s);
    }
}

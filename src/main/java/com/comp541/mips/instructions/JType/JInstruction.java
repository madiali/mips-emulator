package com.comp541.mips.instructions.JType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class JInstruction extends JTypeInstruction {
    public JInstruction(int target) {
        super(target);
        this.name = "J";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        pc.setPC((pc.getPC() & 0xF0000000) | (target << 2));
    }
}

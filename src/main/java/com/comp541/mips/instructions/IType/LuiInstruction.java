package com.comp541.mips.instructions.IType;

import com.comp541.mips.ProgramCounter;
import com.comp541.mips.Registers;
import com.comp541.mips.memory.MemoryMapper;

public class LuiInstruction extends ITypeInstruction {
    public LuiInstruction(int t, int immediate) {
        super(t, 0, immediate);
        this.name = "LUI";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        reg.setRegister(t, immediate << 16);
        pc.incrementPC(4);
    }

    @Override
    public String toString() {
        return name + " " + Registers.registerToName(t) + ", " + String.format("0x%04X", immediate);
    }
}

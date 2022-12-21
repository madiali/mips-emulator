package MIPS_Emulator.Instructions.RType;

import MIPS_Emulator.MemoryMapper;
import MIPS_Emulator.ProgramCounter;
import MIPS_Emulator.Registers;

public class JrInstruction extends RTypeInstruction {
    public JrInstruction(int s) {
        super(0, s, 0);
        this.name = "JR";
    }

    @Override
    public void execute(ProgramCounter pc, MemoryMapper mem, Registers reg) {
        pc.setPC(reg.getRegister(s));
    }
}

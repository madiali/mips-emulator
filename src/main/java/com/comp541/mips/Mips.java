package com.comp541.mips;

import com.comp541.mips.instructions.Instruction;
import com.comp541.mips.memory.InstructionMemory;
import com.comp541.mips.memory.MemoryMapper;
import com.comp541.mips.memory.MemoryUnit;

import java.util.List;
import java.util.Map;

public class Mips {
    private ProgramCounter pc;
    public Map<Class, List<MemoryUnit>> memDict;
    public InstructionMemory instrMem;
    public MemoryMapper memory;
    public Registers reg;
    public String name;
    public float clockSpeed;

    /**
     * @param pc
     * @param memDict
     * @param reg        default value null
     * @param name       default value ""
     * @param clockSpeed default value 0
     */
    public Mips(
            int pc, Map<Class, List<MemoryUnit>> memDict, Registers reg, String name, float clockSpeed) {
        this.pc = new ProgramCounter(pc);
        this.memDict = memDict;
        this.instrMem = (InstructionMemory) memDict.get(InstructionMemory.class).get(0);
        this.memory = (MemoryMapper) memDict.get(MemoryMapper.class).get(0);
        this.reg = (reg == null) ? new Registers() : reg;
        this.name = (name == null) ? "" : name;
        this.clockSpeed = (clockSpeed <= 0) ? 0 : clockSpeed;
    }

    public int getPC() {
        return pc.getPC();
    }

    public void setPC(int newPC) {
        pc.setPC(newPC);
    }

    public InstructionMemory getInstrMem() {
        return instrMem;
    }

    public MemoryMapper getMemory() {
        return memory;
    }

    public Registers getReg() {
        return reg;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public float getClockSpeed() {
        return clockSpeed;
    }

    public void setClockSpeed(float newClockSpeed) {
        clockSpeed = newClockSpeed;
    }

    /**
     * Execute the Instruction at the current pc value.
     */
    public void executeNext() {
        instrMem.getInstruction(pc.getPC()).execute(pc, memory, reg);
    }

    /**
     * Execute the next instruction and return the instruction that was just executed.
     *
     * <p>Used in ExecuteAllThrottled to save work. Improved clock speed by ~5 MHz for Rubik's.
     *
     * @return Instruction that was executed
     */
    public Instruction executeNextAndReturnInstruction() {
        Instruction executed = instrMem.getInstruction(pc.getPC());
        executed.execute(pc, memory, reg);
        return executed;
    }

    public void executeAll() {
        while (pc.getPC() < instrMem.getSize()) {
            instrMem.getInstruction(pc.getPC()).execute(pc, memory, reg);
        }
    }
}

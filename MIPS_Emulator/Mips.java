package MIPS_Emulator;

import java.util.*;

public class Mips {
    private ProgramCounter pc;
	public Map<Class, List<MemoryUnit>> memDict;
	public InstructionMemory instrMem;
	public MemoryMapper memory;
	public Registers reg;
	public String name;
	public float clockSpeed;

	public Mips(int pc, Map<Class, List<MemoryUnit>> memDict, Registers reg, String name, float clockSpeed) {
		this.pc = new ProgramCounter(pc);
		this.memDict = memDict;
		this.instrMem = (InstructionMemory) memDict[typeof(InstructionMemory)][0];
		this.memory = (MemoryMapper) memDict[typeof(MemoryMapper)][0];
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

	public void executeNext() {
		InstrMem.getInstruction(pc).execute(pc, memory, reg);
	}

	public void executeAll() {
		while (pc < InstrMem.size) {
			InstrMem.getInstruction(pc).execute(pc, memory, reg);
		}
	}
}

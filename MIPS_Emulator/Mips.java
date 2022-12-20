package MIPS_Emulator;

import java.util.*;

public class Mips {
    private int pc;
	public Map<Class, List<MemoryUnit>> MemDict;

	public int Pc;
	public InstructionMemory InstrMem;
	public MemoryMapper Memory;
	public Registers Reg;
	public String Name;
	public float ClockSpeed;

	public Mips(int pc, Map<Class, List<MemoryUnit>> memDict, Registers reg = null, string name = "", float clockSpeed = 0) {
		this.pc = pc;
		this.MemDict = memDict;
		this.InstrMem = (InstructionMemory) memDict[typeof(InstructionMemory)][0];
		this.Memory = (MemoryMapper) memDict[typeof(MemoryMapper)][0];
		this.Reg = reg ?? new Registers();
		this.Name = name;
		this.ClockSpeed = clockSpeed;
	}

    public void SetPC(int val) {
        pc = val;
        Pc = val;
    }

    public InstructionMemory GetInstrMem() {
        return InstrMem;
    }

    public MemoryMapper GetMemory() {
        return Memory;
    }

    public Registers GetReg() {
        return Reg;
    }

    public String GetName() {
        return Name;
    }

    public void SetName(String newName) {
        Name = newName;
    }

    public float GetClockSpeed() {
        return ClockSpeed;
    }

    public void SetClockSpeed(float newClockSpeed) {
        ClockSpeed = newClockSpeed;
    }

	public void ExecuteNext() {
		InstrMem.GetInstruction(pc).Execute(Memory, Reg);
	}

	public void ExecuteAll() {
		while (pc < InstrMem.Size) {
			InstrMem.GetInstruction(pc).Execute(Memory, Reg);
		}
	}
}

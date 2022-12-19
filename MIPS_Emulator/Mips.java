package MIPS_Emulator;

public class Mips {
    private int pc;
	public IDictionary<Type, List<MemoryUnit>> MemDict;

	public int Pc;
	public InstructionMemory InstrMem;
	public MemoryMapper Memory;
	public Registers Reg;
	public String Name;
	public float ClockSpeed;

	public Mips(int pc, IDictionary<Type, List<MemoryUnit>> memDict, Registers reg = null, string name = "", float clockSpeed = 0) {
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

	public void ExecuteNext() {
		InstrMem.GetInstruction(pc).Execute(Memory, Reg);
	}

	public void ExecuteAll() {
		while (pc < InstrMem.Size) {
			InstrMem.GetInstruction(pc).Execute(Memory, Reg);
		}
	}
}

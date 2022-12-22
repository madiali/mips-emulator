package mips;

public class ProgramCounter {
    private int pc;

    public ProgramCounter(int pc) {
        this.pc = pc;
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int newPC) {
        pc = newPC;
    }

    public void incrementPC(int val) {
        pc = pc + val;
    }
}

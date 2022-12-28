package controller;

import mips.Mips;
import mips.Registers;

public class MipsController {
    private Mips mips;

    public MipsController(Mips mips) {
        if (mips == null) {
            throw new IllegalArgumentException();
        }
        this.mips = mips;
    }

    public int getPC() {
        return mips.getPC();
    }

    public String getName() {
        return mips.getName();
    }

    public float getClockSpeed() {
        return mips.getClockSpeed();
    }

    public void setClockSpeed(float newClockSpeed) {
        mips.setClockSpeed(newClockSpeed);
    }

    public void executeNext() {
        mips.executeNext();
    }

    public void executeAll() {
        mips.executeAll();
    }

    public int getReg(int regNum) {
        return mips.getReg().getRegister(regNum);
    }

    public void setReg(int regNum, int val) {
        mips.getReg().setRegister(regNum, val);
    }

    public void regToName(int regNum) {
        Registers.registerToName(regNum);
    }

    // TODO: add Accelerometer getter / setter (might need to adjust Mips implementation)
    // TODO: add relevant InstructionMemory methods
    // TODO: add other relevant memory methods
}

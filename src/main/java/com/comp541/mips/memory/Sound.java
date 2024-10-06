package com.comp541.mips.memory;

public class Sound implements MemoryUnit {
    private int wordSize = 4;
    private int period;

    public Sound() {
        this.period = 0;
    }

    @Override
    public int getMemoryUnit(int index) {
        return period;
    }

    @Override
    public void setMemoryUnit(int index, int value) {
        period = value;
    }

    @Override
    public int getSize() {
        return wordSize;
    }

    @Override
    public int getWordSize() {
        return wordSize;
    }
}

package com.comp541.mips.memory;

public class AccelerometerY implements MemoryUnit {
    private int wordSize;
    private int yValue = 255;

    /**
     * size unused, default value 1 wordSize default value 4
     */
    public AccelerometerY() {
        this(1, 4);
    }

    // Original code has size parameter but doesn't do anything with it, not sure why it's there
    // It also has public uint Size => WordSize, so it looks like size == wordSize

    /**
     * @param size     unused, default value 1
     * @param wordSize default value 4
     */
    public AccelerometerY(int size, int wordSize) {
        this.wordSize = wordSize;
    }

    @Override
    public int getMemoryUnit(int index) {
        return yValue;
    }

    @Override
    public void setMemoryUnit(int index, int value) {
        yValue = value;
    }

    @Override
    public int getSize() {
        return wordSize;
    }

    @Override
    public int getWordSize() {
        return wordSize;
    }

    public int getYValue() {
        return yValue;
    }

    public void setYValue(int yValue) {
        this.yValue = yValue;
    }
}

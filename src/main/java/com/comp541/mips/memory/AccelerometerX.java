package com.comp541.mips.memory;

public class AccelerometerX implements MemoryUnit {
    private int wordSize;
    private int xValue = 255;

    /**
     * size unused, default value 1 wordSize default value 4
     */
    public AccelerometerX() {
        this(1, 4);
    }

    // Original code has size parameter but doesn't do anything with it, not sure why it's there
    // It also has public uint Size => WordSize, so it looks like size == wordSize

    /**
     * @param size     unused, default value 1
     * @param wordSize default value 4
     */
    public AccelerometerX(int size, int wordSize) {
        this.wordSize = wordSize;
    }

    @Override
    public int getMemoryUnit(int index) {
        return xValue;
    }

    @Override
    public void setMemoryUnit(int index, int value) {
        xValue = value;
    }

    @Override
    public int getSize() {
        return wordSize;
    }

    @Override
    public int getWordSize() {
        return wordSize;
    }

    public int getXValue() {
        return xValue;
    }

    public void setXValue(int xValue) {
        this.xValue = xValue;
    }
}

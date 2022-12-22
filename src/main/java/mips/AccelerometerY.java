package mips;

public class AccelerometerY implements MemoryUnit {
    private final int WORD_SIZE = 4;
    private int yValue = 255;

    public AccelerometerY() {
        this(1, 4);
    }

    public AccelerometerY(int size, int wordSize) {}

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
        return WORD_SIZE;
    }

    @Override
    public int getWordSize() {
        return WORD_SIZE;
    }
}

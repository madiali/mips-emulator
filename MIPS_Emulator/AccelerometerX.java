package MIPS_Emulator;

public class AccelerometerX implements MemoryUnit {
    private final int WORD_SIZE = 4;
    private int xValue = 255;

    public AccelerometerX() {
        this(1, 4);
    }

    public AccelerometerX(int size, int wordSize) {}

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
        return WORD_SIZE;
    }

    @Override
    public int getWordSize() {
        return WORD_SIZE;
    }
}

package mips;

public class Accelerometer implements MemoryUnit {
    private int wordSize;
    private AccelerometerX accelX;
    private AccelerometerY accelY;

    public Accelerometer() {
        this(1, 4);
    }

    public Accelerometer(int size, int wordSize) {
        this.accelX = new AccelerometerX();
        this.accelY = new AccelerometerY();
    }

    @Override
    public int getMemoryUnit(int index) {
        return (accelX.getMemoryUnit(0) << 16) | (accelY.getMemoryUnit(0));
    }

    /* Not sure how to implement this method.
     * Seems like we will need to be able to change acceleration values at the GUI stage.
     * However, this method in the original seems empty?
     * Also, I believe it would be easier if we could set individual accelX / accelY values,
     * but, as of now, the method does not behave in this manner.
     * In the same sense, it would be nice if getMemoryUnit could access individual accelX
     * / accelY values. */
    @Override
    public void setMemoryUnit(int index, int value) {
        // Upper 16 bits hold accelX, lower 16 bits hold accelY
        accelX.setMemoryUnit(0, value >> 16);
        accelY.setMemoryUnit(0, value & 0x0000FFFF);
    }

    @Override
    public int getSize() {
        return wordSize;
    }

    @Override
    public int getWordSize() {
        return wordSize;
    }

    public AccelerometerX getAccelX() {
        return accelX;
    }

    public AccelerometerY getAccelY() {
        return accelY;
    }
}

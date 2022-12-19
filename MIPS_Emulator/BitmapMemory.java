public class BitmapMemory implements MemoryUnit{
    private int wordSize;
    private final int[] memory;

    public BitmapMemory(int size, int wordSize) {
        this.memory = new int[size];
        this.wordSize = wordSize;
    }

    public BitmapMemory(int size) {
        this.memory = new int[size];
        this.wordSize = 4;
    }

    public BitmapMemory(int[] memory, int wordSize) {
        this.memory = memory;
        this.wordSize = wordSize;
    }

    public BitmapMemory(int[] memory) {
        this.memory = memory;
        this.wordSize = 4;
    }

    @Override
    public int getMemoryUnit(int index) {
        if (index % this.wordSize == 0)
            return this.memory[(int) (index / this.wordSize)];
        else
            throw new IllegalArgumentException(String.format("Index (%d) into bitmap memory is not a multiple of word size (%d)", index, this.wordSize));
    }

    @Override
    public void setMemoryUnit(int index, int value) {
        if (index % this.wordSize == 0)
            this.memory[(int) (index / this.wordSize)] = value;
        else
            throw new IllegalArgumentException(String.format("Index (%d) into bitmap memory is not a multiple of word size (%d)", index, this.wordSize));
    }

    @Override
    public int getSize() {
        return this.memory.length * this.wordSize;
    }

    @Override
    public int getWordSize() {
        return this.wordSize;
    }
}

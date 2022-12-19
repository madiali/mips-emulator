public class BitmapMemory implements MemoryUnit{
    private long _wordSize;
    private final long[] _memory;

    // Java doesn't like it if the size is declared as long since it's used as an array subscript
    // Have to keep it an int then
    public BitmapMemory(int size, long wordSize) {
        _memory = new long[size];
        _wordSize = wordSize;
    }

    public BitmapMemory(int size) {
        _memory = new long[size];
        _wordSize = 4;
    }

    public BitmapMemory(long[] memory, long wordSize) {
        _memory = memory;
        _wordSize = wordSize;
    }

    public BitmapMemory(long[] memory) {
        _memory = memory;
        _wordSize = 4;
    }

    @Override
    public long getMemoryUnit(long index) {
        if (index % _wordSize == 0)
            return _memory[(int) (index / _wordSize)];
        else
            throw new IllegalArgumentException(String.format("Index (%d) into bitmap memory is not a multiple of word size (%d)", index, _wordSize));
    }

    @Override
    public void setMemoryUnit(long index, int value) {
        if (index % _wordSize == 0)
            _memory[(int) (index / _wordSize)] = value;
        else
            throw new IllegalArgumentException(String.format("Index (%d) into bitmap memory is not a multiple of word size (%d)", index, _wordSize));
    }

    @Override
    public long getSize() {
        return (long) _memory.length * _wordSize;
    }

    @Override
    public long getWordSize() {
        return _wordSize;
    }
}

public class SpriteMemory implements MemoryUnit{

    private long _spriteX;
    private long _spriteY;

    public long getSpriteX() {
        return _spriteX;
    }

    private void setSpriteX(long spriteX) {
        _spriteX = spriteX;
    }

    public long getSpriteY() {
        return _spriteY;
    }

    private void setSpriteY(long spriteY) {
        _spriteY = spriteY;
    }

    @Override
    public long getMemoryUnit(long index) {
        return index == 0 ? _spriteY : _spriteX;
    }

    @Override
    public void setMemoryUnit(long index, int value) {
        if (index == 0)
            _spriteY = value;
        else
            _spriteX = value;
    }

    @Override
    public long getSize() {
        return 8;
    }

    @Override
    public long getWordSize() {
        return 4;
    }

    // The original constructor also has an empty body
    public SpriteMemory(long size, long wordSize) {

    }

    // Original constructor has default value 4 for wordSize
    // No default values in Java, so overload the constructor
    public SpriteMemory(long size) {

    }
}

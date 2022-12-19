public class SpriteBitmapMemory extends BitmapMemory {

    public SpriteBitmapMemory(int size, long wordSize) {
        super(size, wordSize);
    }

    public SpriteBitmapMemory(int size) {
        super(size);
    }

    public SpriteBitmapMemory(long[] memory, long wordSize) {
        super(memory, wordSize);
    }

    public SpriteBitmapMemory(long[] memory) {
        super(memory);
    }
}

package mips;

public class SpriteBitmapMemory extends BitmapMemory {

  public SpriteBitmapMemory(int size, int wordSize) {
    super(size, wordSize);
  }

  /** wordSize default value 4 in superclass constructor */
  public SpriteBitmapMemory(int size) {
    super(size);
  }

  public SpriteBitmapMemory(int[] memory, int wordSize) {
    super(memory, wordSize);
  }

  /** wordSize default value 4 in superclass constructor */
  public SpriteBitmapMemory(int[] memory) {
    super(memory);
  }
}

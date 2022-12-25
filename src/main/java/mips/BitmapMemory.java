package mips;

public class BitmapMemory implements MemoryUnit {
  private int wordSize;
  private final int[] memory;

  /**
   * @param size
   * @param wordSize default value 4
   */
  public BitmapMemory(int size, int wordSize) {
    this.memory = new int[size];
    this.wordSize = wordSize;
  }

  /**
   * @param size wordSize default value 4
   */
  public BitmapMemory(int size) {
    this(size, 4);
  }

  /**
   * @param memory
   * @param wordSize default value 4
   */
  public BitmapMemory(int[] memory, int wordSize) {
    this.memory = memory;
    this.wordSize = wordSize;
  }

  /**
   * @param memory wordSize default value 4
   */
  public BitmapMemory(int[] memory) {
    this(memory, 4);
  }

  @Override
  public int getMemoryUnit(int index) {
    if (index % this.wordSize == 0) {
      return this.memory[index / this.wordSize];
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Index (%d) into bitmap memory is not a multiple of word size (%d)",
              index, this.wordSize));
    }
  }

  @Override
  public void setMemoryUnit(int index, int value) {
    if (index % this.wordSize == 0) {
      this.memory[index / this.wordSize] = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Index (%d) into bitmap memory is not a multiple of word size (%d)",
              index, this.wordSize));
    }
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

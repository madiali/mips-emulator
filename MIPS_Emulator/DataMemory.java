public class DataMemory implements MemoryUnit {
  private int wordSize;
  private final int[] memory;

  public DataMemory(int size, int wordSize) {
    this.memory = new int[size];
    this.wordSize = wordSize;
  }

  /**
   * wordSize default value 4
   */
  public DataMemory(int size) {
    this.memory = new int[size];
    this.wordSize = 4;
  }

  public DataMemory(int[] memory, int wordSize) {
    this.memory = memory;
    this.wordSize = wordSize;
  }

  /**
   * wordSize default value 4
   */
  public DataMemory(int[] memory) {
    this.memory = memory;
    this.wordSize = 4;
  }

  @Override
  public int getMemoryUnit(int index) {
    if (index % this.wordSize == 0) {
      return this.memory[
          index
              / this
                  .wordSize]; // This is always an integer since result of mod is 0, don't need to
                              // cast or floor
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Index (%d) into data memory is not a multiple of word size (%d)",
              index, this.wordSize));
    }
  }

  @Override
  public void setMemoryUnit(int index, int value) {
    if (index % this.wordSize == 0) {
      this.memory[(int) (index / this.wordSize)] = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Index (%d) into data memory is not a multiple of word size (%d)",
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

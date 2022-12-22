package mips;

public class ScreenMemory extends DataMemory {
  public ScreenMemory(int size, int wordSize) {
    super(size, wordSize);
  }

  /** wordSize default value 4 in superclass constructor */
  public ScreenMemory(int size) {
    super(size);
  }

  public ScreenMemory(int[] memory, int wordSize) {
    super(memory, wordSize);
  }

  // This is not in the original code, which doesn't give wordSize a default value of 4
  // But I'm keeping this here since it isn't already used anywhere else so shouldn't affect
  // anything
  /** wordSize default value 4 in superclass constructor */
  public ScreenMemory(int[] memory) {
    super(memory);
  }
}

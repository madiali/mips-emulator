package mips.memory;

public class SpriteMemory implements MemoryUnit {

  private int spriteX;
  private int spriteY;

  public int getSpriteX() {
    return this.spriteX;
  }

  private void setSpriteX(int spriteX) {
    this.spriteX = spriteX;
  }

  public int getSpriteY() {
    return this.spriteY;
  }

  private void setSpriteY(int spriteY) {
    this.spriteY = spriteY;
  }

  @Override
  public int getMemoryUnit(int index) {
    return index == 0 ? this.spriteY : this.spriteX;
  }

  @Override
  public void setMemoryUnit(int index, int value) {
    if (index == 0) {
      this.spriteY = value;
    } else {
      this.spriteX = value;
    }
  }

  /**
   * @return 8
   */
  @Override
  public int getSize() {
    return 8;
  }

  /**
   * @return 4
   */
  @Override
  public int getWordSize() {
    return 4;
  }

  /**
   * Constructor body empty, like in original code
   *
   * @param size
   * @param wordSize default value 4
   */
  public SpriteMemory(int size, int wordSize) {}

  // Original constructor has default value 4 for wordSize
  // No default values in Java, so overload the constructor
  /**
   * Constructor body empty
   *
   * @param size
   */
  public SpriteMemory(int size) {}
}

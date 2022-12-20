package MIPS_Emulator;

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

  // The original C# constructor has an empty body
  // After some googling, if there is no constructor or a constructor with no parameters, then the
  // compiler automatically
  //      creates a constructor with no arguments and sets fields to default (in the language)
  // values.
  // The above doesn't apply here, i.e. I don't think a constructor with no body gets an
  // automatically generated body.
  // Leaving this constructor without a body...
  public SpriteMemory(int size, int wordSize) {}

  // Original constructor has default value 4 for wordSize
  // No default values in Java, so overload the constructor
  public SpriteMemory(int size) {}
}

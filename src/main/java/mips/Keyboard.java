package mips;

public class Keyboard implements MemoryUnit {
  private int keyCode = 0;

  public Keyboard() {
    this(4, 4);
  }

  public Keyboard(int size, int wordSize) {}

  @Override
  public int getMemoryUnit(int index) {
    return keyCode;
  }

  @Override
  public void setMemoryUnit(int index, int value) {
    keyCode = value;
  }

  @Override
  public int getSize() {
    return 4;
  }

  @Override
  public int getWordSize() {
    return 4;
  }
}

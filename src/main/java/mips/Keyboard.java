package mips;

public class Keyboard implements MemoryUnit {
  private int keyCode = 0;

  /** Constructor has empty body */
  public Keyboard() {
    this(4, 4);
  }

  /**
   * Constructor has empty body
   *
   * @param size unused
   * @param wordSize unused
   */
  public Keyboard(int size, int wordSize) {}

  /**
   * This just returns Keyboard's keyCode field regardless of the index. Only has the index
   * parameter because it's part of the interface.
   */
  @Override
  public int getMemoryUnit(int index) {
    return keyCode;
  }

  /** Original code has an empty setter body. */
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

  public void setKeycode(int value) {
    keyCode = value;
  }
}

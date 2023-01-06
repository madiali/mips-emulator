package mips.memory;

public interface MemoryUnit {
  int getMemoryUnit(int index);

  void setMemoryUnit(int index, int value);

  int getSize();

  int getWordSize();
}

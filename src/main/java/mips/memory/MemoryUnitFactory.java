/**
 * This file is needed because of a line in ProgramLoader.cs. mem = (MemoryUnit)
 * Activator.createInstance(t, args), where t is a MemoryUnit type passed as a parameter and args is
 * {length, wordSize}, {0, wordSize}, or {} (no args). No direct equivalent in Java, so we use a
 * Factory.
 */
package mips.memory;

public class MemoryUnitFactory {
  public MemoryUnit createMemoryUnit(String type, int length, int wordSize) {
    if (type.equals("InstructionMemory")) {
      return new InstructionMemory(length, wordSize);
    } else if (type.equals("DataMemory")) {
      return new DataMemory(length, wordSize);
    } else if (type.equals("BitmapMemory")) {
      return new BitmapMemory(length, wordSize);
    } else if (type.equals("ScreenMemory")) {
      return new ScreenMemory(length, wordSize);
    } else if (type.equals("Keyboard")) {
      return new Keyboard(length, wordSize);
    } else if (type.equals("Accelerometer")) {
      return new Accelerometer(length, wordSize);
    } else if (type.equals("Sound")) {
      throw new IllegalArgumentException("We have not yet implemented Sound");
    } else {
      throw new IllegalArgumentException("Invalid MemoryUnit type provided: " + type);
    }
  }

  /**
   * @param type NOT InstructionMemory, DataMemory, BitmapMemory, or ScreenMemory (which require
   *     more constructor arguments), which will throw IllegalArgumentException
   * @return
   */
  public MemoryUnit createMemoryUnit(String type) {
    if (type.equals("InstructionMemory")) {
      throw new IllegalArgumentException(
          "InstructionMemory requires length, initFile, or wordSize");
    } else if (type.equals("DataMemory")) {
      throw new IllegalArgumentException("DataMemory requires length, initFile, or wordSize");
    } else if (type.equals("BitmapMemory")) {
      throw new IllegalArgumentException("BitmapMemory requires length, initFile, or wordSize");
    } else if (type.equals("ScreenMemory")) {
      throw new IllegalArgumentException("ScreenMemory requires length, initFile, or wordSize");
    } else if (type.equals("Keyboard")) {
      return new Keyboard();
    } else if (type.equals("Accelerometer")) {
      return new Accelerometer();
    } else if (type.equals("Sound")) {
      throw new IllegalArgumentException("We have not yet implemented Sound");
    } else {
      throw new IllegalArgumentException("Invalid MemoryUnit type provided: " + type);
    }
  }
}

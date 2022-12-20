public class Registers {
  private final int[] registers;

  public Registers() {
    // Default value for `uint` is 0
    // Default value for `int` is 0, so this is fine
    registers = new int[32];
  }

  public int getRegister(int regNumber) {
    return (regNumber == 0) ? 0 : registers[regNumber];
  }

  public void setRegister(int regNumber, int value) {
    registers[regNumber] = value;
  }

  public static String registerToName(int regNumber) {
    if (regNumber == 0) {
      return "$zero";
    } else if (regNumber == 1) {
      return "$at";
    } else if (regNumber <= 3) {
      return "$v"
          + (regNumber
              - 2); // This automatically converts the int to a String in C# and Java, so it's fine
    } else if (regNumber <= 7) {
      return "$a" + (regNumber - 4);
    } else if (regNumber <= 15) {
      return "$t" + (regNumber - 8);
    } else if (regNumber <= 23) {
      return "$s" + (regNumber - 16);
    } else if (regNumber <= 25) {
      return "$t" + (regNumber - 16);
    } else if (regNumber <= 27) {
      return "$k" + (regNumber - 26);
    } else if (regNumber == 28) {
      return "$gp";
    } else if (regNumber == 29) {
      return "$sp";
    } else if (regNumber == 30) {
      return "$fp";
    } else if (regNumber == 31) {
      return "$ra";
    }

    return "$" + regNumber;
  }
}

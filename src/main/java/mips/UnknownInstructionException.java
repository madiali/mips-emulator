package mips;

public class UnknownInstructionException extends IllegalArgumentException {
  public UnknownInstructionException(int instruction) {
    super(String.format("Unknown instruction: 0x%08X", instruction));
  }
}

package MIPS_Emulator;

import jdk.jshell.spi.ExecutionControl;

public class UnknownInstructionException extends ExecutionControl.NotImplementedException {
    public UnknownInstructionException(int instruction) {
        super(String.format("Unknown instruction: 0x%08X", instruction));
    }
}

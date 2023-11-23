package com.comp541.mips.exception;

public class UnmappedAddressException extends IllegalArgumentException {
    public UnmappedAddressException(int address) {
        super(String.format("unable to find memory unit mapped to 0x%08X", address));
    }
}

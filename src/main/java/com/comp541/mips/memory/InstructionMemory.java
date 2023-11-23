package com.comp541.mips.memory;

import com.comp541.mips.instructions.Instruction;

public class InstructionMemory implements MemoryUnit {
    private final Instruction[] iMem;
    private final int wordSizeLog;
    private int wordSize;
    private InstructionFactory instrFact;

    /**
     * @param size
     * @param wordSize default value 4
     */
    public InstructionMemory(int size, int wordSize) {
        iMem = new Instruction[size];
        instrFact = new InstructionFactory();
        this.wordSize = wordSize;
        // Calculate log_2(wordSize) using change of base formula
        wordSizeLog = (int) (Math.log(wordSize) / Math.log(2));
        // Comparing double to int using != or == is sus, but the original code does it, and Java does
        // return true for 0.0==0, 1.0==1, etc. Should be okay.
        if (Math.pow(2, wordSizeLog) != wordSize) {
            throw new IllegalArgumentException(
                    "WordSize " + wordSize + " in instruction memory is not a power of two");
        }
    }

    /**
     * @param size wordSize default value 4
     */
    public InstructionMemory(int size) {
        this(size, 4);
    }

    /**
     * @param instructions
     * @param wordSize     default value 4
     */
    public InstructionMemory(Instruction[] instructions, int wordSize) {
        iMem = instructions;
        instrFact = new InstructionFactory();
        this.wordSize = wordSize;
        wordSizeLog = (int) (Math.log(wordSize) / Math.log(2));
        // Comparing double to int using != or == is sus, but the original code does it, and Java does
        // return true for 0.0==0, 1.0==1, etc. Should be okay.
        if (Math.pow(2, wordSizeLog) != wordSize) {
            throw new IllegalArgumentException(
                    "WordSize " + wordSize + " in instruction memory is not a power of two");
        }
    }

    /**
     * @param instructions wordSize default value 4
     */
    public InstructionMemory(Instruction[] instructions) {
        this(instructions, 4);
    }

    /**
     * DO NOT CALL THIS METHOD. The original code throws a NotImplementedException when this method is
     * called. However, we can't throw an Exception here since the method needs to return an integer.
     * *
     */
    @Override
    public int getMemoryUnit(int pc) {
        System.out.println(
                "InstructionMemory.java should throw a NotImplementedException but can't because the method needs to return something...");
        return -1;
    }

    @Override
    public void setMemoryUnit(int pc, int value) {
        if (pc % wordSize == 0) {
            iMem[pc / wordSize] = instrFact.createInstruction(value);
        } else {
            throw new IllegalArgumentException(
                    "Index ("
                            + pc
                            + ") into instruction memory is not a multiple of word size ("
                            + wordSize
                            + ")");
        }
    }

    @Override
    public int getSize() {
        return iMem.length * wordSize;
    }

    @Override
    public int getWordSize() {
        return wordSize;
    }

    public int getWordSizeLog() {
        return wordSizeLog;
    }

    public InstructionFactory getInstrFact() {
        return instrFact;
    }

    public void setInstrFact(InstructionFactory instrFact) {
        this.instrFact = instrFact;
    }

    public Instruction getInstruction(int pc) {
        if (pc % wordSize == 0) {
            return iMem[(pc & 0xffff) >> wordSizeLog];
        } else {
            throw new IllegalArgumentException(
                    "Index ("
                            + pc
                            + ") into instruction memory is not a multiple of word size ("
                            + wordSize
                            + ")");
        }
    }
}

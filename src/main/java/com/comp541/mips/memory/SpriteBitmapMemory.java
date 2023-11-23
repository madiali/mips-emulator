package com.comp541.mips.memory;

public class SpriteBitmapMemory extends BitmapMemory {

    /**
     * @param size
     * @param wordSize default value 4
     */
    public SpriteBitmapMemory(int size, int wordSize) {
        super(size, wordSize);
    }

    /**
     * wordSize default value 4 in superclass constructor
     */
    public SpriteBitmapMemory(int size) {
        super(size);
    }

    /**
     * @param memory
     * @param wordSize default value 4
     */
    public SpriteBitmapMemory(int[] memory, int wordSize) {
        super(memory, wordSize);
    }

    /**
     * wordSize default value 4 in superclass constructor
     */
    public SpriteBitmapMemory(int[] memory) {
        super(memory);
    }
}

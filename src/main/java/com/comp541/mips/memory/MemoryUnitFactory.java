/**
 * This file is needed because of a line in ProgramLoader.cs. mem = (MemoryUnit)
 * Activator.createInstance(t, args), where t is a MemoryUnit type passed as a parameter and args is
 * {length, wordSize}, {0, wordSize}, or {} (no args). No direct equivalent in Java, so we use a
 * Factory.
 */
package com.comp541.mips.memory;

public class MemoryUnitFactory {
    public MemoryUnit createMemoryUnit(String type, int length, int wordSize) {
        return switch (type) {
            case "InstructionMemory" -> new InstructionMemory(length, wordSize);
            case "DataMemory" -> new DataMemory(length, wordSize);
            case "BitmapMemory" -> new BitmapMemory(length, wordSize);
            case "ScreenMemory" -> new ScreenMemory(length, wordSize);
            case "Keyboard" -> new Keyboard(length, wordSize);
            case "Accelerometer" -> new Accelerometer(length, wordSize);
            // TODO: This case should log a message to the user that they shouldn't have provided a length/initFile/wordSize for Sound
            case "Sound" -> new Sound();
            default -> throw new IllegalArgumentException("Invalid MemoryUnit type provided: " + type);
        };
    }

    /**
     * @param type NOT InstructionMemory, DataMemory, BitmapMemory, or ScreenMemory (which require
     *             more constructor arguments), which will throw IllegalArgumentException
     * @return
     */
    public MemoryUnit createMemoryUnit(String type) {
        return switch (type) {
            case "InstructionMemory" -> throw new IllegalArgumentException(
                    "InstructionMemory requires length, initFile, or wordSize in the JSON");
            case "DataMemory" ->
                    throw new IllegalArgumentException("DataMemory requires length, initFile, or wordSize in the JSON");
            case "BitmapMemory" ->
                    throw new IllegalArgumentException("BitmapMemory requires length, initFile, or wordSize in the JSON");
            case "ScreenMemory" ->
                    throw new IllegalArgumentException("ScreenMemory requires length, initFile, or wordSize in the JSON");
            case "Keyboard" -> new Keyboard();
            case "Accelerometer" -> new Accelerometer();
            case "Sound" -> new Sound();
            default -> throw new IllegalArgumentException("Invalid MemoryUnit type provided in the JSON: " + type);
        };
    }
}

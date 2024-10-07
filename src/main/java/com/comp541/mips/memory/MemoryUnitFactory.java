/**
 * This file is needed because of a line in ProgramLoader.cs. mem = (MemoryUnit)
 * Activator.createInstance(t, args), where t is a MemoryUnit type passed as a parameter and args is
 * {length, wordSize}, {0, wordSize}, or {} (no args). No direct equivalent in Java, so we use a
 * Factory.
 */
package com.comp541.mips.memory;

import static com.comp541.Main.LOGGER;

public class MemoryUnitFactory {
    public MemoryUnit createMemoryUnit(String type, int length, int wordSize) {
        return switch (type) {
            case "InstructionMemory" -> new InstructionMemory(length, wordSize);
            case "DataMemory" -> new DataMemory(length, wordSize);
            case "BitmapMemory" -> new BitmapMemory(length, wordSize);
            case "ScreenMemory" -> new ScreenMemory(length, wordSize);
            case "Keyboard" -> new Keyboard(length, wordSize);
            case "Accelerometer" -> new Accelerometer(length, wordSize);
            case "Sound" -> {
                LOGGER.warn("Sound should not have a length, initFile, or wordSize in the JSON, these values will be ignored, and a default Sound unit with size 4 and wordSize 4 will be created");
                yield new Sound();
            }
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

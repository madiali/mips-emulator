package mips;

/**
 * NOT DONE
 * For now, we've decided to hardcode the project configuration in this file instead of parsing JSON.
 */

import java.io.File;
import java.util.*;

public class ProgramLoader {
    private Mips mips;
    private final String basePath;

    /**
     * @param file If file is in /MIPS_Emulator/, then it should be located with /MIPS_Emulator/{file}
     *             Do not use relative path {file} without the leading /MIPS_Emulator/
     */
    public ProgramLoader(File file) {
        // If file is in /MIPS_Emulator/, the below returns /MIPS_Emulator (without the trailing /)
        this.basePath = file.getParent();
        this.mips = loadMipsFromFile(file);
    }

    private Mips loadMipsFromFile(File file) {
        // TODO: We have not yet implemented sound but will do so soon (TM)
        // SoundModule.waveOut.stop();

        // Skipping all of the JSON stuff. For now, hardcoding instead

        int pc = 0;
        // TODO: Do memDict after implementing BuildMemoryUnits
        String name = "Placeholder, parse the name from JSON later";
        float desiredClockSpeed = 12.5F;

        // TODO: Use mips constructor after implementing BuildMemoryUnits
        return null;
    }

    /**
     * TODO: Should take JToken token parameter
     * @return
     */
    private int parseRequiredNumber() {
        return -1;
    }

    /**
     * TODO: Should take JToken token parameter
     * @return
     */
    private int parseNumber() {
        return -1;
    }

    private int parseNumber(String token) {
        return -1;
    }

    /**
     * TODO: Should take JToken token parameter
     * Finish this after buildMemoryUnit and mapMemoryToAddresses
     * @return
     */
    private Map<Class, List<MemoryUnit>> buildMemoryUnits() {
        Map<Class, List<MemoryUnit>> memoryDict = new HashMap<>();

        List<MappedMemoryUnit> memUnits = new ArrayList<>();

        return null;
    }

    /**
     * Should take a JToken token parameter
     * @return
     */
    private MemoryUnit buildMemoryUnit() {
        return null;
    }

    /**
     * Should take a JToken token parameter
     * @param mem
     * @return
     */
    private MappedMemoryUnit mapMemoryToAddresses(MemoryUnit mem) {
        return null;
    }

    /**
     * Should take a JToken token parameter
     * @return
     */
    private int[] readInitFile() {
        return null;
    }

    private static int parseFormat(String format) {
        return -1;
    }

    private int[] parseInitData(String path, int baseNum) {
        return null;
    }

    private static String cleanLine(String line) {
        return null;
    }
}
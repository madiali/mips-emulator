package MIPS_Emulator;

import java.util.regex.*;

public class MappedMemoryUnit {
    private MemoryUnit memUnit;
    private int startAddr;
    private int endAddr;
    private String name;
    // The original code uses a Regex type, but that doesn't exist in Java
    private final Pattern bitmaskFormat = Pattern.compile("^(0|1)+x*$");

    public MappedMemoryUnit(MemoryUnit memUnit, int startAddr, int endAddr, String name) {
        this.memUnit = memUnit;
        this.startAddr = startAddr;
        this.endAddr = endAddr;
        this.name = (name == null) ? memUnit.getClass().getName() : name;
    }

    /**
     * Overloaded constructor
     * Default value of String name is null in original code
     * @param memUnit
     * @param startAddr
     * @param endAddr
     */
    public MappedMemoryUnit(MemoryUnit memUnit, int startAddr, int endAddr) {
        // If there is no String name argument, then null is the default value
        // In the original class, if name is null, then this.Name is set to memUnit.getClass().getName(), so I simply pass this into the base constructor
        // This is becoming a huge hassle... we might want to just not implement default values in constructors...
        this(memUnit, startAddr, endAddr, null);
    }

    public MappedMemoryUnit(MemoryUnit memUnit, int startAddr, String name) {
        this(memUnit, startAddr, startAddr + memUnit.getSize() - 1, name);
    }

    /**
     * Overloaded constructor
     * If the only args are memUnit and startAddr, then String name has default value null
     * @param memUnit
     * @param startAddr
     */
    public MappedMemoryUnit(MemoryUnit memUnit, int startAddr) {
        this(memUnit, startAddr, null);
    }

    /**
     * WARNING REGARDING BITMASK, READ BELOW
     * @param memUnit
     * @param bitmask If this bitmask is the full 32 bits AND the 31'th bit is 1, then there will be undefined behavior.
     *                This is due to implementation (Integer.parseInt) and uint vs. int.
     * @param name
     */
    public MappedMemoryUnit(MemoryUnit memUnit, String bitmask, String name) {
        String cleanedBitmask = bitmask.trim().toLowerCase().replace("_", "");
        Matcher matcher = bitmaskFormat.matcher(cleanedBitmask);
        boolean matchFound = matcher.find();
        if (!matchFound) {
            throw new IllegalArgumentException("Invalid bitmask: \"" + bitmask + "\", must match regex /" + bitmaskFormat.toString() + "/");
        }
        this.memUnit = memUnit;
        this.startAddr = Integer.parseInt(cleanedBitmask.replace("x", "0"), 2);
        if (this.startAddr < 0) {
            throw new ArithmeticException("StartAddr is negative in MappedMemoryUnit.java. Its value is " + this.startAddr);
        }
        this.endAddr = Integer.parseInt(cleanedBitmask.replace("x", "1"), 2);
        if (this.endAddr < 0) {
            throw new ArithmeticException("EndAddr is negative in MappedMemoryUnit.java. Its value is " + this.endAddr);
        }
        // This replaces the null coalescing (??) and null conditional (x?.y) operators in the original code
        this.name = (name == null) ? ((memUnit == null) ? null : memUnit.getClass().getName()) : name;
    }

    /**
     * WARNING REGARDING BITMASK, READ BELOW
     * Overloaded constructor
     * If the only args are memUnit and bitmask, then String name is null
     * @param memUnit
     * @param bitmask If this bitmask is the full 32 bits AND the 31'th bit is 1, then there will be undefined behavior.
     *                This is due to implementation (Integer.parseInt) and uint vs. int.
     */
    public MappedMemoryUnit(MemoryUnit memUnit, String bitmask) {
        this(memUnit, bitmask, null);
    }

    public int getMappedMemoryUnit(int index) {
        return memUnit.getMemoryUnit(index);
    }

    public void setMappedMemoryUnit(int index, int value) {
        memUnit.setMemoryUnit(index, value);
    }

    public MemoryUnit getMemUnit() {
        return memUnit;
    }

    public int getStartAddr() {
        return startAddr;
    }

    public int getEndAddr() {
        return endAddr;
    }

    public int getSize() {
        return memUnit.getSize();
    }

    public int getWordSize() {
        return memUnit.getWordSize();
    }

    public String getName() {
        return name;
    }
}

# MIPS Emulator

We ported https://github.com/jordanel/mips-emulator to Java so the app works on any OS. Credit to Jordan Elliot et al. for their original and awesome work!

Below is documentation from the OG repo. Please read below to understand how to configure your project with a required JSON file. Check the [Wiki](https://github.com/madiali/mips-emulator/wiki) for [known bugs/limitations](https://github.com/madiali/mips-emulator/wiki/Known-bugs-and-limitations).

---

MIPS Emulator is an emulator designed to support customized MIPS processors using memory-mapped I/O based on designs from UNC Chapel Hill’s Digital Logic course. Please add any issues found to the issues page. This emulator is more restrictive than the FPGAs used in the course, so cases in which something works on the board but not on the emulator may not be issues. On the other hand, anything that works on the emulator but not on the board is likely an issue and should be reported.

## Project Files ##

A MIPS Emulator project is configured using a **required JSON file**. See the examples in [src/test/TestProjects](https://github.com/madiali/mips-emulator/tree/main/src/test/TestProjects). [full_test](https://github.com/madiali/mips-emulator/tree/main/src/test/TestProjects/full_test), [imem_test](https://github.com/madiali/mips-emulator/tree/main/src/test/TestProjects/imem_test), and [PoopEmoji 💩](https://github.com/madiali/mips-emulator/tree/main/src/test/TestProjects/PoopEmoji) are from the OG repo. The JSON files in full_test, CatsAndDogs, and Rubik's follow project specifications and won't need to be modified much, if at all, for your project.

This JSON file contains project-level information as well as configuration and mapping information for any memory units needed by the project. A project can also include multiple memory initialization files, used to set the starting values of configured memories. Numeric values may also be passed as a hexadecimal or binary string prefixed with `0x` and `0b` respectively.

### Project File Elements ###

- projectName: The name of your project. Will appear on the emulator title bar.
- programCounter (optional): The starting program counter value.
- memories (array): A list of all mapped memory units needed by your MIPS processor.
    - type: The C# class of the memory unit (see below for a list of default types).
    - name (optional): The name of the memory unit. Used for display purposes in the Memory Mapper Viewer.
    - bitmask (optional*): A bitmask representing the mapped range of the memory unit. Must follow regex `^(0|1)+x*$`
    - startAddr (optional*): The starting mapped address for the memory unit.
    - endAddr (optional*): The ending mapped address for the memory unit.
    - size (optional*): The mapped size (number of addresses) of the memory unit.
    - length (optional): The number of memory locations in the memory unit. If not present, will be set to the size of the init file.
    - wordSize (optional): The size of a word in this memory unit (defaults to 4). Determines the number of addresses between values. Must be a power of 2.
    - initFile (optional): Information about the memory intialization file for this memory unit.
        - filepath: The path to the memory initialization file (typically a .txt or .mem file).
        - format (optional): The representation of values in the memory initialization file (hex, dec, bin). Defaults to hex.

### Additional Mapping Information ###

Arbitrarily sized memory units (InstructionMemory, DataMemory, ScreenMemory, BitmapMemory) must be configured using a length or initFile.

Any memory unit intended to be mapped and accessible to the MIPS program must have one of the following combinations of elements:
- bitmask
- startAddr
- startAddr, endAddr
- startAddr, size

### Memory Unit Types ###

- InstructionMemory - Read only memory containing the instructions of the MIPS program
- DataMemory - Read/write memory containing data used by the MIPS program. Can be used in place of unimplemented memory units
- BitmapMemory - Read only memory containing the pixel values for all 16x16 bitmaps used by the MIPS program
- ScreenMemory - Read/write memory containing the bitmap values to be displayed on the screen
- Keyboard - Read only memory containing the current keyboard scan code
- ~~Sound - Read only memory containing the period of the waveform used by the sound module~~ (not supported)
- Accelerometer - Read only memory containing the X and Y accelerometer values in the following format: `{7'b0, accelX, 7'b0, accelY}`
- ~~AccelerometerX - Read only memory containing the X value of the accelerometer module~~ (not supported; use Accelerometer)
- ~~AccelerometerY - Read only memory containing the Y value of the accelerometer module~~ (not supported; use Accelerometer)

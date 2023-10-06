# MIPS Emulator

<p align="center">
  <img src="https://i.imgur.com/y0gdKg6.gif">
</p>

MIPS Emulator is an emulator designed to support customized MIPS processors using memory-mapped I/O based on designs from UNC Chapel Hill’s COMP 541 Digital Logic course.

The original [MIPS emulator](https://github.com/jordanel/mips-emulator) was programmed in C#. We ported it to Java to allow the app to work on any OS. Credit to Jordan Elliot et al. for their original and awesome work!

## Install

Download the [latest release](https://github.com/madiali/mips-emulator/releases/latest).

If you're using Windows, also consider the [original MIPS emulator](https://github.com/jordanel/mips-emulator) (C#, Windows-only).

## Setup

The `.jar` has been tested on JDK 11 (check your version with `java --version`).

Before running the `.jar`, set up a directory with a **required configuration** `.json` file and
your project's `.mem` files. You will be prompted to load a JSON file when the application runs.

<p align="center">
  <img width="250" align="center" src="https://i.imgur.com/IV7vATs.png"> 
  <figcaption>Example project structure - <a href="src/test/TestProjects/CatsAndDogs">CatsAndDogs</a></figcaption>
</p>

<p align="center">
  <img width="250" src="https://i.imgur.com/QqoMuB9.png"> 
  <figcaption><a href="src/test/TestProjects/CatsAndDogs/catsAndDogs.json">catsAndDogs.json</a></figcaption>
</p>

You can find an example JSON file `catsAndDogs.json` at the link above.
Modify the `.json` for your own needs. Mostly everything should stay the same, but at minimum,
you will need to provide the names of your `.mem` files.

You can create additional Data Memory mappings to view mapped data memory values in the emulator.
See [Rubik's](src/test/TestProjects/Rubik's/rubiks.json) for an example.

There are more examples of project JSON files in the OG [MIPS emulator repo](https://github.com/jordanel/mips-emulator/tree/master/projects).
Specifically, [full_test](https://github.com/jordanel/mips-emulator/tree/master/projects/full_test) is a great place to start.
However, note that we have not implemented `Sound` in this emulator, so don't map `Sound` in a JSON file for our emulator.
Similarly, don't map `AccelerometerX` or `AccelerometerY` (just use `Accelerometer`, which has all the
capabilities of both `AccelerometerX` and `AccelerometerY`).

This should be all you need, but if you need advanced capabilities, see [below](#project-files) for more information.

## Run

```sh
java -jar <path-to-mips-emulator.jar>
```

## Bugs and limitations

If you run into any issues, check [the wiki page](https://github.com/madiali/mips-emulator/wiki/Known-bugs-and-limitations).

Report issues at [Issues](https://github.com/madiali/mips-emulator/issues).

---

# Other info paraphrased from OG MIPS emulator's README

Please add any issues found to the issues page. This emulator is more restrictive than the FPGAs used in the course, so cases in which something works on the board but not on the emulator may not be issues. On the other hand, anything that works on the emulator but not on the board is likely an issue and should be reported.

## Project files

A MIPS Emulator project is configured using a **required JSON file**, as described in the [Setup](#setup) section.

This JSON file contains project-level information as well as configuration and mapping information for any memory units needed by the project. A project can also include multiple memory initialization files, used to set the starting values of configured memories. Numeric values may also be passed as a hexadecimal or binary string prefixed with `0x` and `0b` respectively.

### Project JSON file elements

- projectName: The name of your project. Will appear on the emulator title bar.
- programCounter (optional): The starting program counter value.
- memories (array): A list of all mapped memory units needed by your MIPS processor.
    - type: The class of the memory unit (see below for a list of default types).
    - name (optional): The name of the memory unit. Used for display purposes in the Memory Mapper Viewer.
    - bitmask (optional*): A bitmask representing the mapped range of the memory unit. Must follow regex `^(0|1)+x*$`
    - startAddr (optional*): The starting mapped address for the memory unit.
    - endAddr (optional*): The ending mapped address for the memory unit.
    - size (optional*): The mapped size (number of addresses) of the memory unit.
    - length (optional): The number of memory locations in the memory unit. If not present, will be set to the size of the init file.
    - wordSize (optional): The size of a word in this memory unit (defaults to 4). Determines the number of addresses between values. Must be a power of 2.
    - initFile (optional): Information about the memory initialization file for this memory unit.
        - filepath: The path to the memory initialization file (typically a .txt or .mem file).
        - format (optional): The representation of values in the memory initialization file (hex, dec, bin). Defaults to hex.

### Additional mapping information

Arbitrarily sized memory units (InstructionMemory, DataMemory, ScreenMemory, BitmapMemory) must be configured using a length or initFile.

Any memory unit intended to be mapped and accessible to the MIPS program must have one of the following combinations of elements:
- bitmask
- startAddr
- startAddr, endAddr
- startAddr, size

### Memory unit types

- InstructionMemory - Read only memory containing the instructions of the MIPS program
- DataMemory - Read/write memory containing data used by the MIPS program. Can be used in place of unimplemented memory units
- BitmapMemory - Read only memory containing the pixel values for all 16x16 bitmaps used by the MIPS program
- ScreenMemory - Read/write memory containing the bitmap values to be displayed on the screen
- Keyboard - Read only memory containing the current keyboard scan code
- ~~Sound - Read only memory containing the period of the waveform used by the sound module~~ (not implemented)
- Accelerometer - Read only memory containing the X and Y accelerometer values in the following format: `{7'b0, accelX, 7'b0, accelY}`
- ~~AccelerometerX - Read only memory containing the X value of the accelerometer module~~ (not implemented; use Accelerometer)
- ~~AccelerometerY - Read only memory containing the Y value of the accelerometer module~~ (not implemented; use Accelerometer)

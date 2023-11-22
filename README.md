# MIPS Emulator

<p align="center">
  <img src="https://i.imgur.com/y0gdKg6.gif">
</p>

<p align="center">
  <a href="https://www.youtube.com/watch?v=GOZdBHTAFI0"><img src="https://img.youtube.com/vi/GOZdBHTAFI0/0.jpg" alt="full demo video"></a>
</p>

<p align="center">
  <a href="https://www.youtube.com/watch?v=GOZdBHTAFI0">Demo/instructions</a>
</p>

MIPS Emulator is a cross-platform simulator for final projects in [COMP 541](https://comp541.web.unc.edu/) (Digital Logic and Computer Design) at [UNC](https://www.unc.edu/). It simulates customized MIPS processors using memory-mapped I/O and devices, such as accelerometer, keyboard, screen, and LED. Whereas flashing the MIPS assembly project onto the FPGA board can take upwards of 10 minutes per flash, this emulator allows for instant testing and debugging.

We ported the original [MIPS emulator](https://github.com/jordanel/mips-emulator) to Java to make the program work on any OS. Credit to [@jordanel](https://github.com/jordanel), [@jsettlem](https://github.com/jsettlem), [@swali-unc](https://github.com/swali-unc), and [@MarkovInequality](https://github.com/MarkovInequality) for their awesome work!

## Install

### macOS/Linux

#### Automatic

```bash
curl -s "https://raw.githubusercontent.com/madiali/mips-emulator/main/src/main/sh/install.sh" | bash
```

When done, restart your terminal. You should then be able to run `mips-em` to launch MIPS Emulator. This should print out a message and open your file browser. Skip to [Usage](#usage).

If this script does not work on your computer, follow the manual steps below.

#### Manual

Run the following:

```bash
curl -s "https://raw.githubusercontent.com/madiali/mips-emulator/main/src/main/sh/install.sh" | sed -n '/<<< Install Java <<</q;p' | bash
```

This is the same as the above command but runs the script only up until it's done installing a compatible Java version and setting it as the default. This section of the script should be guaranteed to work.

Then, download the JAR file from the [latest release](https://github.com/madiali/mips-emulator/releases/latest). Run it with `java -jar <path-to-mips-emulator.jar>`. Running with this command avoids permission issues that may arise when double-clicking the file. Skip to [Usage](#usage).

### Windows

Download the JAR file from the [latest release](https://github.com/madiali/mips-emulator/releases/latest).

Then, run `java -jar <path-to-mips-emulator.jar>`. If this prints a message and opens your file browser, you're all set (skip to [Usage](#usage))! Otherwise, your Java version is incompatible, so follow the instructions below.

#### JDK 17+FX installation

You need JDK 17+ with JavaFX (GUI dependency) bundled. To download, go to [Azul's website](https://www.azul.com/downloads/?version=java-17-lts&os=windows&architecture=x86-64-bit&package=jdk-fx#zulu). This link includes tags for Java 17 (LTS), Windows x86_64, and JDK FX.

Download the `.msi` file.

<div align="center">

![.msi](https://i.imgur.com/xqBnzlc.png)

</div>

Double-click the `.msi`.

After you run it and click Next one time, you will be on the Custom Setup screen, where you will see a red X by the text `Set JAVA_HOME variable`. Click on it and select `Will be installed on local hard drive`.

You should now see this (no red X):

<div align="center">

![Java setup](https://i.imgur.com/1sLcDoq.png)

</div>

Click Next and then Install (administrator permissions required). When done, click Finish.

#### Verify Java version

Open PowerShell. If you already had a session running, close it and restart. Run the following:

```powershell
echo $env:JAVA_HOME
java --version
```

You should see something like

```text
C:\Program Files\Zulu\zulu-17\
openjdk 17.0.8.1 2023-08-24 LTS
OpenJDK Runtime Environment Zulu17.44+53-CA (build 17.0.8.1+1-LTS)
OpenJDK 64-Bit Server VM Zulu17.44+53-CA (build 17.0.8.1+1-LTS, mixed mode, sharing)
```

If so, run `java -jar <path-to-mips-emulator.jar>`. This should print a message and open File Explorer. If so, you're all set! Continue to [Usage](#usage).

Otherwise, your `JAVA_HOME` environment variable and `java --version` outputs are incorrect, or you just need to restart your computer. To set `JAVA_HOME`, follow this [StackOverflow answer](https://stackoverflow.com/a/6521412/18479243). The default installation path should be `C:\Program Files\Zulu\zulu-17\`, as printed above. When complete, run MIPS Emulator via `java -jar`, as mentioned in the previous paragraph. If this still does not work, restart your computer and try again.

## Usage

### Basic setup

Create a directory with a **required** project configuration `.json` file and
your project's `.mem` files. You will be prompted to load this JSON file in your file explorer when the application runs.

The directory should look like this:

```text
CatsAndDogs
├── bmem.mem
├── CatsAndDogs.json
├── dmem.mem
├── imem.mem
└── smem.mem
```

Our default configuration JSON file is [`CatsAndDogs.json`](src/test/ExampleProjects/CatsAndDogs/CatsAndDogs.json). You should download it and modify it, if necessary, for your own project.

You shouldn't need to change many fields, if any, since it uses the same memory mappings as the ones in the project specification. If your memory files are named differently from `{b,d,i,s}mem.mem`, then change your memory file names, or change the names in the JSON file.

The `"type"` fields contain the special types `Keyboard`, `Accelerometer`, etc., to tell the emulator that the values at those memory addresses (specified by the `"startAddr"` and/or `"length"` fields, if necessary) are special and should be used for I/O or other purposes. For example, type `InstructionMemory` tells the emulator to interpret those values as instructions to be executed.

```json
{
  "type": "InstructionMemory",
  "initFile": {
    "filepath": "imem.mem",
    "format": "hex"
  }
}
```

`CatsAndDogs.json` has the special types `InstructionMemory`, `DataMemory`, `BitmapMemory`, `ScreenMemory`, `Keyboard`, and `Accelerometer`.

LED and Sound are not considered special types because they do not serve any special purpose (e.g., I/O) in the emulator. That is, the emulator does not show 12 LED's on the screen or play sound (unsupported, as of now). Thus, LED and Sound are mapped with type `DataMemory` at the appropriate memory addresses so that you can at least inspect the values at those memory addresses in the Other Memory tab in the emulator.

```json
{
  "type": "DataMemory",
  "name": "Sound",
  "startAddr": "0x10030008",
  "length": 1
}
```

<p align="center">
  <img src="https://i.imgur.com/IZRknzr.png">
</p>

For sound, specifically, the original Windows-only [MIPS Emulator](https://github.com/jordanel/mips-emulator) can play sound, so it supports `Sound` as a type in the JSON. Therefore, to test sound, if you are on Windows, we would suggest downloading the original MIPS Emulator and specifying `"type": "Sound"` in the JSON.

### Additional memory mappings

If you have additional memory mappings in your project, you can create those mappings in the JSON with type `DataMemory` to view the values in the emulator, similar to the Sound mapping shown above. For example, see [rubiks.json](src/test/ExampleProjects/Rubik's/rubiks.json), which has 7 additional mappings. For example,

```json
{
  "comment": "Two sound registers to play chords.",
  "type": "DataMemory",
  "name": "sound_2",
  "startAddr": "0x10000008",
  "length": 1
}
```

The value(s) would be displayed in the Other Memory tab.

<p align="center">
  <img src="https://i.imgur.com/lpIXERi.png">
</p>

Our emulator does not see nor support any of your Verilog code, of course, so anything handled by Verilog will not show up in the emulator. For example, if you use Verilog to generate random numbers and store them at an address, you shouldn't map that in the JSON (except maybe for documentation purposes).

This should be all you need. For advanced mapping options, see [Advanced configuration](#advanced-configuration).

## Issues

Please report issues with MIPS emulator (e.g., bug report, feature request, usage question) at [Issues](https://github.com/madiali/mips-emulator/issues).

## Developer information

We welcome contributions! See [Contributing](.github/CONTRIBUTING.md).

## Advanced configuration

For more information about the project JSON file (i.e., all possible mapping options, which we support but don't really see a need for), see the original MIPS Emulator's [README](https://github.com/jordanel/mips-emulator).

However, note that many example JSON files in that repository specify type `Sound`, which we do not support and causes our emulator to crash, so do not put `"type": "Sound"` in a JSON file for our emulator. In general, any JSON file that works for this project will work for the original MIPS Emulator. However, a JSON file from the original MIPS Emulator's repository that specifies `"type": "Sound"` will not work in this project.

Also, the original MIPS Emulator supports types `AccelerometerX` and `AccelerometerY`, which are unnecessary. As shown in our example JSON files, just use `Accelerometer` because `Accelerometer == AccelerometerX + AccelerometerY`.

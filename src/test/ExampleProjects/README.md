# README

Please watch the demo video shown at the top of the [README](https://github.com/madiali/mips-emulator), if you haven't already.

The two directories here are example projects containing the 5 required files for MIPS Emulator:

1. Configuration JSON file
2. `{b,d,i,s}mem.mem`

You can run MIPS Emulator and load either JSON file in these directories to run the projects.

[CatsAndDogs.json](CatsAndDogs/CatsAndDogs.json) is our default JSON file that specifies the memory types `InstructionMemory`, `DataMemory`, `BitmapMemory`, `ScreenMemory`, `Keyboard`, and `Accelerometer`. It also contains LED and Sound, specified with type `DataMemory` so that their values can be viewed in the Other Memory tab in the emulator. All are specified with the default memory addresses given by the project specification, so you probably won't need to change that.

[rubiks.json](Rubik's/rubiks.json) contains 7 additional memory mappings, also specified with type `DataMemory`.

To test MIPS Emulator, controls for these projects are provided:

## CatsAndDogs

Use the accelerometer y slider to move the umbrella.

Cat and dog sprite positions are not randomized when run in the emulator because Madison handled RNG in Verilog. Of course, the emulator cannot simulate anything handled in Verilog.

## Rubik's

[Project demo](https://youtu.be/CWI60TmpJHM)

The black screen at the start of the program is intentional (but likely a bad idea, in retrospect, because a black screen makes it look like my project isn't working).

The controls follow [Rubik's cube notation](https://youtu.be/24eHm4ri8WM).

## Face moves

|  Key  | Action |
| :---: | :----: |
|   u   |   U    |
|   d   |   D    |
|   l   |   L    |
|   r   |   R    |
|   f   |   F    |
|   b   |   B    |

## Slice moves

|  Key  | Action |
| :---: | :----: |
|   m   |   M    |
|   e   |   E    |
|   s   |   S    |

## Rotations

|      Input      | Action |
| :-------------: | :----: |
|        x        |   x    |
|        y        |   y    |
|        z        |   z    |
| accelerometer x |   x    |
| accelerometer y |   y    |

## Move configuration

|  Key  |                    Action                     |
| :---: | :-------------------------------------------: |
|   1   |     Make future moves clockwise (e.g., U)     |
|   2   |   Make future moves 180 degrees (e.g., U2)    |
|   '   | Make future moves counterclockwise (e.g., U') |
|   w   |       Make future moves wide (e.g., u)        |

w can be composed with any direction 1, 2, or '. Therefore, u, u2, and u' can all be performed.

## Miscellaneous

|  Key  |         Action         |
| :---: | :--------------------: |
|   g   |     Solve the cube     |
|   n   |     Next scramble      |
|   p   |   Previous scramble    |
|   c   | Create random scramble |
|   t   |   Toggle `is_legit`    |
|   v   |  Toggle `timer_mode`   |

I handled RNG in Verilog, so I'm pretty sure creating a random scramble doesn't work in the emulator and would just set the scramble to the next one.

`is_legit` is a boolean for whether the program will pause and play music when the cube is solved. For example, when g is pressed, `is_legit` will always be false.

`timer_mode` is shown in the demo.

# Controls

[Project demo](https://youtu.be/CWI60TmpJHM)

The black screen at the start of the program is intentional (but likely a bad idea, in retrospect, because a black screen makes it look like my project isn't working).

These controls follow [Rubik's cube notation](https://youtu.be/24eHm4ri8WM).

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

## Miscellanous

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

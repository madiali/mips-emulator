## Need to check

Here are some things that need to be tested:

* `int` and `uint` in Instructions and MappedMemoryUnit (Integer.parseInt)
  * If we notice that a "bitmask", whatever that is, is the full 32 bits AND the 31'th (sign) bit is used, then something will go wrong. Otherwise it's fine.
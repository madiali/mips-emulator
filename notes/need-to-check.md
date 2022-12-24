## Need to check

Here are some things that need to be tested:

* `int` and `uint` in Instructions and MappedMemoryUnit (Integer.parseInt)
  * If we notice that a "bitmask", whatever that is, is the full 32 bits AND the 31'th (sign) bit is used, then something will go wrong. Otherwise it's fine.
  * Also in MemoryMapper, if the addresses exceed 2 million, that'd be bad.
* `MemoryMapper`: should `findContainingUnit` and `resolveAddress` be `static` methods?
* IType instructions: Access fields with this.s, this.t, this.imm, or super.s, super.t, super.imm, or no prefix all have the same behavior?
* Registers: The original code says `registers` is `readonly` but has a setter method that modifies `registers`?
* ProgramLoader: parseNumber should also use parseUnsignedInt? This may cause unintended negative values...
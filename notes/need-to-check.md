## Need to check

Here are some things that need to be tested:

* `int` and `uint` in Instructions and MappedMemoryUnit (Integer.parseInt)
    * If we notice that a "bitmask", whatever that is, is the full 32 bits AND the 31'th (sign) bit is used, then
      something will go wrong. Otherwise it's fine.
    * Also in MemoryMapper, if the addresses exceed 2 million, that'd be bad.
* `MemoryMapper`: should `findContainingUnit` and `resolveAddress` be `static` methods?
* IType instructions: Access fields with this.s, this.t, this.imm, or super.s, super.t, super.imm, or no prefix all have
  the same behavior?
* Registers: The original code says `registers` is `readonly` but has a setter method that modifies `registers`?
* ProgramLoader: parseNumber should also use parseUnsignedInt? This may cause unintended negative values...
* ProgramLoaderTest.parsesHexAndBinValues: DataMemory mapped to 0xDEADBEEF (negative) sorted correctly due to
  MemoryMapper using Integer.compareUnsigned, but the start address is negative.
    * If this causes problems (it might be fine though), we can say that the JSON shouldn't map to an address above
      0x7FFFFFFF.
    * Make sure that all int fields are checked as `Strings` or `int`s.
* VGADisplayBMPController: Will creating files in /img work when we package the program into a .jar?
  * Access memory mapper for bmem and smem?
* AccelerometerController and any others that remove components:
    * Because of how removing a component works, if the FXML layout ever changes, then remove logic needs to be recoded.
      It would be super super easy to do though, just change some types.
* KeyboardController: read docstring for differences between ours and OG (for program efficiency)
Note: my imem.mem and dmem.mem files are intentionally not uploaded.
But you can still look at full_test.json for an example project configuration file.

In particular, if you happen to encounter an UnmappedAddressException for mapping between addresses 0x10010000 and 0x10020000,
see the first DataMemory object in the JSON for a workaround.

You should ignore the objects after LED mapped to 0x1003000c since those are unique to my project.
They also don't do anything special in the emulator since there's no Verilog support for them in the emulator, but mapping them does allow
you to see the values stored at those addresses.

Jesse
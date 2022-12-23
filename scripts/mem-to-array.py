"""Convert bmem/smem to array of unsigned ints or imem/dmem to array of signed ints, and store results in a .txt file."""

SIGNED = False

filename = input("Enter the name of the .mem file: ")
if ".mem" in filename:
    print("Don't include .mem")
    exit()
if "imem" in filename.lower() or "dmem" in filename.lower():
    SIGNED = True

print(f"SIGNED = {SIGNED}")
    
def hex_to_dec(hexstr: str, signed: bool):
    # Assumes hexstr is stripped (i.e. doesn't have spaces or newlines)
    # If signed == True, then interpret the hex str as a signed 2's complement number
    val = int(hexstr, 16)
    bits = len(hexstr) * 4
    if signed and (val & (1 << (bits - 1))):
        val -= 1 << bits
    return val

# 'w' mode automatically deletes the file's contents if it already exists
res_file = open(f'{filename}.txt', 'w')
with open(filename + '.mem', 'r') as mem_file:
    res_file.write('{')
    mem_file_lines = mem_file.readlines()

    i = 0
    # Iterate to next to last line to write the last line without a trailing ', '
    while i < len(mem_file_lines) - 1:
        if '//' not in mem_file_lines[i]:
            res_file.write(str(hex_to_dec(mem_file_lines[i].strip(), SIGNED)) + ', ')
        i += 1
    # There won't be a comment at the last line, no need to check
    res_file.write(str(hex_to_dec(mem_file_lines[i].strip(), SIGNED)))
    res_file.write('}')
res_file.close()

"""Given a .mem file of hex values in the same directory as this script, convert to array of unsigned ints and store results in a .txt file."""

filename = input("Enter the name of the .mem file, don't include .mem: ")
# 'w' mode automatically deletes the file's contents if it already exists
res_file = open(f'{filename}.txt', 'w')
with open(filename + '.mem', 'r') as mem_file:
    res_file.write('{')
    mem_file_lines = mem_file.readlines()
    i = 0
    # Iterate to next to last line to write the last line without a trailing ', '
    while i < len(mem_file_lines) - 1:
        if '//' not in mem_file_lines[i]:
            res_file.write(str(int(mem_file_lines[i], 16)) + ', ')
        i += 1
    # There won't be a comment at the last line, no need to check
    res_file.write(str(int(mem_file_lines[i], 16)))
    res_file.write('}')
res_file.close()
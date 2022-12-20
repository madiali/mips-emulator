/*
 * Note: The Program class is declared as internal in the original code.
 * From https://stackoverflow.com/questions/5981107/is-there-anything-like-an-internal-class-in-java, the Java equivalent is not using an access modifier.
 * Also, this code definitely isn't complete because it relies on ProgramLoader and Mips.
 * Specifically, we need to use getter methods in the while loop around line 40, but Mips.java needs to be completed first.
 */
import java.util.Scanner;
import java.io.File;

class Program {
    public static void main(String[] args) {
        ProgramLoader loader;
        String filename;
        Scanner scan = new Scanner(System.in);

        // Determine config file location
        if (args.length > 0) {
            filename = args[0];
        } else {
            System.out.println("Config file path required. Please enter its location.");
            filename = scan.nextLine();
        }

        // Loads the emulator configuration from the config file
        try {
            loader = new ProgramLoader(new File(filename));
        } catch (Exception e) {
            System.out.println("Encountered error on initialization. Press Enter to exit.");
            // nextLine() blocks until Enter is pressed, so this is the same behavior as the original code, except the user must press Enter to exit.
            scan.nextLine();
            return;
        }

        System.out.println("Emulator successfully configured. Ready to run.");
        System.out.println("Press n to execute the next instruction, r to display register contents, and i to display instruction memory contents.");
        // TODO: Improve this
        System.out.println("You need to press Enter after each of the above due to Java user input limitations, unfortunately.");

        while (true) {
            String input = scan.nextLine();
            char token = input.charAt(input.length() - 1);
            Mips mips = loader.getMips();
            switch (token) {
                case 'n':
                    mips.executeNext();
                    System.out.println("PC: " + mips.Pc + "\t Instr: " + mips.InstrMem.GetInstruction(mips.Pc));
                    break;
                case 'r':
                    System.out.println("Register Contents:");
                    for (int i = 0; i < 32; i++) {
                        System.out.println("" + Registers.RegisterToName(i) + "\t" + mips.Reg[i]);          // Use "" + to ensure that the values are treated as strings and concatenated
                    }
                    break;
                case 'i':
                    System.out.println("Instruction Memory Contents:");
                    for (int i = 0; i < mips.InstrMem.Size; i += mips.InstrMem.wordSize) {
                        System.out.println((mips.getPc() == i) ? ">" : " " + "0x" + Integer.toHexString(i) + " | " + mips.InstrMem.getInstruction(i));
                    }
                    break;
            }
        }
    }
}

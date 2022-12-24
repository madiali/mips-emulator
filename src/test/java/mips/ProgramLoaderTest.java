package mips;

import mips.instructions.Instruction;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ProgramLoaderTest {
    private ProgramLoader target;

    @Before
    public void setup() throws IOException {
        File file = new File("src/test/TestProjects/Project1/no_errors.json");
        target = new ProgramLoader(file);
    }

    @Test
    public void pcSetFromFile() {
        Mips mips = target.getMips();
        assertEquals(4, mips.getPC());
    }

    @Test
    public void instructionsInitializedBlanksIgnored() {
        assertEquals(49 * 4, target.getMips().getInstrMem().getSize());
    }

    /**
     * This test uses toString, I'm gonna add overridden toString methods to Instructions
     */
    @Test
    public void instructionsInitializedNoComment() {
        InstructionFactory instrFact = new InstructionFactory();
        // expected = LUI $t0, 0xFFFF
        Instruction expected = instrFact.createInstruction(0x3c08ffff);
        Instruction targetInstruction = target.getMips().getInstrMem().getInstruction(4);
        assertEquals(expected.getClass(), targetInstruction.getClass());
    }
}

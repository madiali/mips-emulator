package mips;

import com.comp541.mips.Mips;
import com.comp541.mips.ProgramLoader;
import com.comp541.mips.instructions.Instruction;
import com.comp541.mips.memory.DataMemory;
import com.comp541.mips.memory.InstructionFactory;
import com.comp541.mips.memory.MappedMemoryUnit;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.comp541.GUI.MainController.EXAMPLE_PROJECT_DIR;
import static org.junit.Assert.*;

public class ProgramLoaderTest {
    private ProgramLoader target;
    private static final Path JUNIT_PROJECT_DIR = Paths.get("src/test/ForUnitTestsPlsIgnore");

    @Before
    public void setup() {
        File noErrors = new File(JUNIT_PROJECT_DIR.resolve("no_errors.json").toString());
        // If this fails, all tests are failed even if they would otherwise pass
        // But this is fine, ProgramLoader(noErrors) should not fail
        // And if it does, that's very bad, so it's fine if all these tests fail (attracts attention)
        try {
            target = new ProgramLoader(noErrors);
        } catch (Exception e) { fail(); }
    }

    @Test
    public void catsAndDogs() {
        File catsAndDogs = new File(EXAMPLE_PROJECT_DIR.resolve("CatsAndDogs").resolve("CatsAndDogs.json").toString());
        try {
            new ProgramLoader(catsAndDogs);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void rubiks() {
        File rubiks = new File(EXAMPLE_PROJECT_DIR.resolve("Rubik's").resolve("rubiks.json").toString());
        try {
            new ProgramLoader(rubiks);
        } catch (Exception e) {
            fail();
        }
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

    @Test
    public void instructionsInitializedNoComment() {
        InstructionFactory instrFact = new InstructionFactory();
        Instruction expected = instrFact.createInstruction(0x3c08ffff);
        assertEquals(expected.toString(), target.getMips().getInstrMem().getInstruction(4).toString());
    }

    @Test
    public void instructionsInitializedWithComment() {
        InstructionFactory instrFact = new InstructionFactory();
        Instruction expected = instrFact.createInstruction(0x201d003c);
        assertEquals(expected.toString(), target.getMips().getInstrMem().getInstruction(0).toString());
    }

    @Test
    public void memoryInitializedWithStartAddr() {
        assertEquals(3, target.getMips().getMemory().getMemoryUnit(4));
    }

    @Test
    public void memoryInitializedWithStartAndEndAddr() {
        assertEquals(0xf00, target.getMips().getMemory().getMemoryUnit(400));
    }

    @Test
    public void memoryInitializedWithStartAddrAndSize() {
        assertEquals(0xf00, target.getMips().getMemory().getMemoryUnit(2048));
    }

    /**
     * TODO: need better error message
     */
    @Test
    public void nonExistentMemoryUnitType() {
        File nonexistentMemoryType = new File(JUNIT_PROJECT_DIR.resolve("nonexistent_memory_type.json").toString());
        try {
            new ProgramLoader(nonexistentMemoryType);
            fail();
        } catch (Exception ignored) {

        }
    }

    /**
     * TODO: same here
     */
    @Test
    public void invalidMemoryUnitType() {
        File invalidMemoryType = new File(JUNIT_PROJECT_DIR.resolve("invalid_memory_type.json").toString());
        try {
            new ProgramLoader(invalidMemoryType);
            fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void parsesHexAndBinValues() {
        MappedMemoryUnit memUnit = target.getMips().getMemory().getMemUnits().stream().filter(mappedMemoryUnit -> mappedMemoryUnit.getName().equals("DataMemory2")).findFirst().orElse(null);
        assert memUnit != null;
        assertTrue(memUnit.getMemUnit() instanceof DataMemory);
        // This passes but the startAddr is negative (but sorted correctly, as if it were unsigned, due
        // to MemoryMapper using Integer.compareUnsigned to sort)
        assertEquals(0xDEADBEEF, memUnit.getStartAddr());
        assertEquals(0b100, memUnit.getSize() / memUnit.getWordSize());
    }
}

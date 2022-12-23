package mips;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataMemoryTest {
    private DataMemory target;

    @Before
    public void setup() {
        int[] data = {1, 2};
        target = new DataMemory(data);
    }

    @Test
    public void setInstructionValidIndex() {
        target = new DataMemory(8);
        target.setMemoryUnit(4, 5);
        assertEquals(5, target.getMemoryUnit(4));
    }

    @Test
    public void setInstructionInvalidIndexThrowsArgumentException() {
        try {
            target.setMemoryUnit(3, 10);
            fail();
        } catch (IllegalArgumentException iae) {

        }
    }

    @Test
    public void getInstructionInvalidIndexThrowsArgumentException() {
        int dataElement;
        try {
           dataElement = target.getMemoryUnit(3);
        } catch (IllegalArgumentException iae) {

        }
    }
}

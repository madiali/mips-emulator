package mips;

import com.comp541.mips.Registers;
import org.junit.Test;

import static com.comp541.Main.LOGGER;
import static org.junit.Assert.assertEquals;

public class RegistersTest {
    private Registers r = new Registers();

    @Test
    public void setZeroRegisterRemainsZero() {
        r.setRegister(0, 5);
        assertEquals(0, r.getRegister(0));
    }

    @Test
    public void testSettingNonZeroRegister() {
        r.setRegister(1, 3);
        assertEquals(3, r.getRegister(1));
    }

    // TODO: Implement better register name test
    @Test
    public void testPrint() {
        for (int i = 0; i < 33; i++) {
            LOGGER.info(Registers.registerToName(i));
        }
    }
}

/**
 * I honestly thought TestThrows was some imported function, turns out it was defined at the bottom
 * :facepalm: Lot of duplicated code here that's functionally fine
 */
package mips;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MappedMemoryUnitTest {
  private MemoryUnit memUnit;

  @Before
  public void setup() {
    memUnit = new DataMemory(new int[] {1, 2, 3, 4});
  }

  @Test
  public void constructWithStartAndEndAddresses() {
    MappedMemoryUnit target = new MappedMemoryUnit(memUnit, 12, 44);
    assertEquals(12, target.getStartAddr());
    assertEquals(44, target.getEndAddr());
  }

  @Test
  public void constructWithStartAddressImplicitEndAddress() {
    MappedMemoryUnit target = new MappedMemoryUnit(memUnit, 12);
    assertEquals(12, target.getStartAddr());
    assertEquals(27, target.getEndAddr());
    assertEquals(memUnit, target.getMemUnit());
  }

  @Test
  public void constructBitmask() {
    MappedMemoryUnit target = new MappedMemoryUnit(memUnit, " _1_0_xXxx_\r\n");
    assertEquals(0b100000, target.getStartAddr());
    assertEquals(0b101111, target.getEndAddr());
    assertEquals(memUnit, target.getMemUnit());
  }

  @Test
  public void constructBitmaskBeginsWithXThrowsArgumentException() {
    try {
      MappedMemoryUnit target = new MappedMemoryUnit(memUnit, "X10XXXX");
      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void constructBitmaskIsAllXThrowsArgumentException() {
    try {
      MappedMemoryUnit target = new MappedMemoryUnit(memUnit, "XXXX");
      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void constructBitmaskXBetweenBitsThrowsArgumentException() {
    try {
      MappedMemoryUnit target = new MappedMemoryUnit(memUnit, "1X0X");
      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void constructBitmaskInvalidCharactersThrowsArgumentException() {
    try {
      MappedMemoryUnit target = new MappedMemoryUnit(memUnit, "1021");
      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void constructBitmaskEmptyThrowsArgumentException() {
    try {
      MappedMemoryUnit target = new MappedMemoryUnit(memUnit, "");
      fail();
    } catch (Exception e) {

    }
  }
}

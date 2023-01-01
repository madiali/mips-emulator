/**
 * The OG code in MainWindow.cs gets the Keyboard field as a MappedMemoryUnit but casts it to
 * Keyboard in the OnKeyDown and OnKeyUp methods? Why doesn't it just make the Keyboard field's type
 * Keyboard from the start then? Gonna go with the OG approach for now...
 *
 * <p>Also, instead of a ScanCodeMapper class, I just use an internal HashMap. The order of entries
 * is the same as keyboard.ini
 *
 * <p>https://github.com/jordanel/mips-emulator/blob/master/MIPS%20Emulator.GUI/keyboard.ini
 */
package controller;

import javafx.scene.input.KeyCode;
import mips.Keyboard;
import mips.MappedMemoryUnit;
import mips.Mips;
import java.util.Map;
import static java.util.Map.entry;

public class KeyboardController {
  private MappedMemoryUnit keyboard;
  /*
  READ IMPORTANT NOTE IN skipped-files.md!! JavaFX won't send an event for a key when it does something in the GUI.
  For example, when you click on a slider and it turns blue, then LEFT and RIGHT will change the slider value and will not
  cause an Event. Here's a perhaps not comprehensive list of keys that we know are affected, based on what GUI component is active:
  LEFT, RIGHT, UP, DOWN, SPACE, HOME, END, PGUP, PGDOWN, TAB

  Most issues can be avoided by not pressing any GUI component. We may add dedicated GUI buttons for the above keys.
  Additionally, JavaFX doesn't distinguish between Left and Right for Windows, Shift, Control.
  It doesn't support Applications key.
  Idk what KeyCode `/~ corresponds to. I think it's KeyCode.DEAD_GRAVE and KeyCode.DEAD_TILDE? Mapped both to the same value.
  The OG code omits scancodes for these very uncommon keys: HELP, Separator, Left MENU, Right MENU
  */
  private Map<KeyCode, Integer> scancodeMapper =
      Map.ofEntries(
          entry(KeyCode.BACK_SPACE, 102),
          entry(KeyCode.TAB, 13),
          entry(KeyCode.ENTER, 90),
          entry(KeyCode.PAUSE, 119),
          entry(KeyCode.CAPS, 88),
          entry(KeyCode.ESCAPE, 118),
          entry(KeyCode.SPACE, 41),
          entry(KeyCode.PAGE_UP, 57469),
          entry(KeyCode.PAGE_DOWN, 57466),
          entry(KeyCode.END, 57449),
          entry(KeyCode.HOME, 57452),
          entry(KeyCode.LEFT, 107),
          entry(KeyCode.UP, 117),
          entry(KeyCode.RIGHT, 57460),
          entry(KeyCode.DOWN, 114),
          entry(KeyCode.PRINTSCREEN, 57468),
          entry(KeyCode.INSERT, 57456),
          entry(KeyCode.DELETE, 57457),
          entry(KeyCode.DIGIT0, 69), // lolz
          entry(KeyCode.DIGIT1, 22),
          entry(KeyCode.DIGIT2, 30),
          entry(KeyCode.DIGIT3, 38),
          entry(KeyCode.DIGIT4, 37),
          entry(KeyCode.DIGIT5, 46),
          entry(KeyCode.DIGIT6, 54),
          entry(KeyCode.DIGIT7, 61),
          entry(KeyCode.DIGIT8, 62),
          entry(KeyCode.DIGIT9, 70),
          entry(KeyCode.A, 28),
          entry(KeyCode.B, 50),
          entry(KeyCode.C, 33),
          entry(KeyCode.D, 35),
          entry(KeyCode.E, 36),
          entry(KeyCode.F, 43),
          entry(KeyCode.G, 52),
          entry(KeyCode.H, 51),
          entry(KeyCode.I, 67),
          entry(KeyCode.J, 59),
          entry(KeyCode.K, 66),
          entry(KeyCode.L, 75),
          entry(KeyCode.M, 58),
          entry(KeyCode.N, 49),
          entry(KeyCode.O, 68),
          entry(KeyCode.P, 77),
          entry(KeyCode.Q, 21),
          entry(KeyCode.R, 45),
          entry(KeyCode.S, 27),
          entry(KeyCode.T, 44),
          entry(KeyCode.U, 60),
          entry(KeyCode.V, 42),
          entry(KeyCode.W, 29),
          entry(KeyCode.X, 34),
          entry(KeyCode.Y, 53),
          entry(KeyCode.Z, 26),
          entry(KeyCode.WINDOWS, 57375),
          entry(KeyCode.NUMPAD0, 112),
          entry(KeyCode.NUMPAD1, 105),
          entry(KeyCode.NUMPAD2, 114),
          entry(KeyCode.NUMPAD3, 122),
          entry(KeyCode.NUMPAD4, 107),
          entry(KeyCode.NUMPAD5, 115),
          entry(KeyCode.NUMPAD6, 116),
          entry(KeyCode.NUMPAD7, 108),
          entry(KeyCode.NUMPAD8, 117),
          entry(KeyCode.NUMPAD9, 125),
          entry(KeyCode.MULTIPLY, 124),
          entry(KeyCode.ADD, 121),
          entry(KeyCode.SUBTRACT, 123),
          entry(KeyCode.DECIMAL, 113),
          entry(KeyCode.DIVIDE, 66),
          entry(KeyCode.F1, 5),
          entry(KeyCode.F2, 6),
          entry(KeyCode.F3, 4),
          entry(KeyCode.F4, 12),
          entry(KeyCode.F5, 3),
          entry(KeyCode.F6, 11),
          entry(KeyCode.F7, 131),
          entry(KeyCode.F8, 10),
          entry(KeyCode.F9, 1),
          entry(KeyCode.F10, 9),
          entry(KeyCode.F11, 120),
          entry(KeyCode.F12, 7),
          entry(KeyCode.NUM_LOCK, 119),
          entry(KeyCode.SCROLL_LOCK, 126),
          entry(KeyCode.SHIFT, 18),
          entry(KeyCode.CONTROL, 20),
          entry(KeyCode.SEMICOLON, 76),
          entry(KeyCode.PLUS, 85),
          entry(KeyCode.COMMA, 65),
          entry(KeyCode.MINUS, 78),
          entry(KeyCode.PERIOD, 73),
          entry(KeyCode.SLASH, 74),
          entry(KeyCode.DEAD_GRAVE, 14),
          entry(KeyCode.DEAD_TILDE, 14),
          entry(KeyCode.BRACELEFT, 84),
          entry(KeyCode.BACK_SLASH, 93),
          entry(KeyCode.BRACERIGHT, 91),
          entry(KeyCode.QUOTE, 82));

  public KeyboardController(Mips mips) {
    MappedMemoryUnit mappedKeyboard = null;
    try {
      mappedKeyboard =
          mips.getMemory().getMemUnits().stream()
              .filter(mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof Keyboard)
              .toList()
              .get(0);
    } catch (ArrayIndexOutOfBoundsException aioobe) {
      // If Keyboard isn't memory mapped (has startAddr or bitmask) in JSON, then the toList() is
      // empty and .get(0) causes
      // IndexOutOfBoundsException. mappedKeyboard remains null.
    }
    this.keyboard = mappedKeyboard;
  }

  public void handleOnKeyDown(KeyCode keycode) {
    if (keyboard != null) {
      Keyboard kb = (Keyboard) keyboard.getMemUnit();
      kb.setKeycode(scancodeMapper.get(keycode));
    }
  }

  public void handleOnKeyUp(KeyCode keycode) {
    if (keyboard != null) {
      Keyboard kb = (Keyboard) keyboard.getMemUnit();
      int scanCode = scancodeMapper.get(keycode);
      if (keycode == KeyCode.PRINTSCREEN) {
        // Special case for print screen
        kb.setKeycode(0xE0F012);
      } else if (scanCode > 0xFF) {
        kb.setKeycode(scanCode | 0xE0F000);
      } else {
        kb.setKeycode(scanCode | 0xF000);
      }
    }
  }
}

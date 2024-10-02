package com.comp541.GUI;

import com.comp541.mips.Mips;
import com.comp541.mips.memory.MemoryUnit;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class LedController {
    private static final int NUM_LEDS = 16;
    private static final Color LED_BLACK = Color.rgb(57, 51, 45);
    private static final Color LED_GREEN = Color.rgb(109, 255, 51);

    private static Circle[] leds;
    private static MemoryUnit ledMemory;

    public LedController(Mips mips, Circle[] leds) {
        try {
            LedController.leds = leds;
            ledMemory =
                    (MemoryUnit)
                            Objects.requireNonNull(mips.getMemory().getMemUnits().stream()
                                            .filter(mappedMemoryUnit -> mappedMemoryUnit.getName().equals("LED"))
                                            .findFirst()
                                            .orElse(null))
                                    .getMemUnit();
            initializeLED();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeLED() {
        for (Circle led : leds) {
            led.setFill(LED_BLACK);
        }
        renderLED();
    }

    public static void renderLED() {
        // We only need the lower 16 bits of the LED value
        int ledValue = ledMemory.getMemoryUnit(0) & 0xFFFF;
        boolean [] bitArray = intToBoolArray(ledValue);

        Platform.runLater(() -> {
            for (int i = 0; i < NUM_LEDS; i++) {
                Color color = bitArray[i] ? LED_GREEN : LED_BLACK;
                leds[i].setFill(color);
            }
        });
    }

    private static boolean[] intToBoolArray(int value) {
        boolean[] bits = new boolean[16];  // Array to store 16 bits

        // Loop over each bit (starting from the most significant bit)
        for (int i = 0; i < 16; i++) {
            // Shift the value right by (15 - i) bits and mask with 1 to get the bit value
            bits[i] = ((value >> (15 - i)) & 1) == 1;
        }

        return bits;
    }
}

package controller;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mips.BitmapMemory;
import mips.Mips;
import mips.ScreenMemory;

public class VgaDisplayController {
    private final static int GRID_WIDTH = 40;
    private final static int GRID_HEIGHT = 30;
    private final static int SPRITE_LENGTH = 16;
    private final static int SPRITE_SIZE = SPRITE_LENGTH * SPRITE_LENGTH;

    private GridPane vgaDisplay;
//    private int[] smem;
//    private int[] bmem;
    private ScreenMemory screenMemory;
    private BitmapMemory bitmapMemory;
    private double pixelSize;

    public VgaDisplayController(Mips mips, GridPane vgaDisplay) {
        this.vgaDisplay = vgaDisplay;
//        smem = ((ScreenMemory) mips.memDict.get(ScreenMemory.class).get(0)).getMemoryClone();
//        bmem = ((BitmapMemory) mips.memDict.get(BitmapMemory.class).get(0)).getMemoryClone();
        screenMemory = (ScreenMemory) mips.memDict.get(ScreenMemory.class).get(0);
        bitmapMemory = (BitmapMemory) mips.memDict.get(BitmapMemory.class).get(0);
        pixelSize = Math.min(vgaDisplay.getWidth() / (GRID_WIDTH * SPRITE_LENGTH), vgaDisplay.getHeight() / (GRID_HEIGHT * SPRITE_LENGTH));
        this.render();
    }

    /* Generates sprite from BitmapMemory */
    private GridPane generateSprite(int spriteNum) {
        GridPane sprite = new GridPane();
        for (int y = 0; y < SPRITE_LENGTH; y++) {
            for (int x = 0; x < SPRITE_LENGTH; x++) {
                Rectangle pixel = hexToRectangle(bitmapMemory.getMemoryUnit(bitmapMemory.getWordSize() * ((spriteNum * SPRITE_SIZE) + (y * SPRITE_LENGTH + x))));
                sprite.add(pixel, x, y);
            }
        }
        return sprite;
    }

    /* Converts integer in Bitmap Memory representing 3-hexit RGB hex string to a Rectangle object */
    private Rectangle hexToRectangle(int hexNumber) {
        int r = (hexNumber & 0xF00) >> 8;
        int g = (hexNumber & 0xF0) >> 4;
        int b = (hexNumber & 0xF);
        // Color.rgb expects a 2-hexit int in the range 0-255 for each color.
        // With just one hexit to work with, the bit is replicated 0x1 -> 0x11, 0x2 -> 0x22, etc.
        Color color = Color.rgb((r << 4) | r, (g << 4) | g, (b << 4) | b);
        return new Rectangle(pixelSize, pixelSize, color);
    }

    public void render() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                int spriteNum = screenMemory.getMemoryUnit(screenMemory.getWordSize() * ((y * GRID_WIDTH) + x));
                GridPane sprite = generateSprite(spriteNum);
                vgaDisplay.add(sprite, x, y);
            }
        }
    }
}

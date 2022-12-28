package GUI.display;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mips.BitmapMemory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BitmapMethods {
    private static final int PIXEL_SIZE = 4;
    private static final int SPRITE_SIZE = 16;

    /* Generates array of sprites from bitmap memory file */
    public static List<GridPane> generateSpriteArray(String bitmapFile, int spriteSize)
            throws IOException {
        List<String> hexValues = Files.readAllLines(Paths.get(bitmapFile));
        Iterator<String> it = hexValues.iterator();
        List<GridPane> spriteArray = new ArrayList<GridPane>();
        while (it.hasNext()) {
            GridPane sprite = new GridPane();
            for (int i = 0; i < spriteSize; i++) {
                for (int j = 0; j < spriteSize; j++) {
                    Rectangle pixel = hexToRectangle(it.next());
                    sprite.add(pixel, j, i);
                }
            }
            spriteArray.add(sprite);
        }
        return spriteArray;
    }

    /* Generates array of sprites of default size 16 from bitmap memory file */
    public static List<GridPane> generateSpriteArray(String bitmapFile) throws IOException {
        return generateSpriteArray(bitmapFile, SPRITE_SIZE);
    }

    /* Generates array of sprites from BitmapMemory */
    public static List<GridPane> generateSpriteArray(BitmapMemory bitmapMemory, int spriteSize)
            throws IOException {
        int[] bmem = bitmapMemory.getMemoryClone();
        validateBitmapMemorySize(bmem.length, spriteSize);

        List<GridPane> spriteArray = new ArrayList<GridPane>();
        for (int numSprite = 0; numSprite < bmem.length / (spriteSize * spriteSize); numSprite++) {
            GridPane sprite = new GridPane();
            for (int i = 0; i < spriteSize; i++) {
                for (int j = 0; j < spriteSize; j++) {
                    int pixelIndex = spriteSize * spriteSize * numSprite + spriteSize * i + j;
                    Rectangle pixel = hexToRectangle(bmem[pixelIndex]);
                    sprite.add(pixel, j, i);
                }
            }
            spriteArray.add(sprite);
        }
        return spriteArray;
    }

    /* Generates array of sprites of default size 16 from Bitmap Memory */
    public static List<GridPane> generateSpriteArray(BitmapMemory bitmapMemory) throws IOException {
        return generateSpriteArray(bitmapMemory, SPRITE_SIZE);
    }

    /* Converts 3-digit hex string in bitmap memory file to a Rectangle object */
    public static Rectangle hexToRectangle(String hexString) {
        Color color = Color.web(hexString);
        return new Rectangle(PIXEL_SIZE, PIXEL_SIZE, color);
    }

    /* Converts integer in Bitmap Memory representing 3-hexit RGB hex string to a Rectangle object */
    public static Rectangle hexToRectangle(int hexNumber) {
        int r = (hexNumber & 0xF00) >> 8;
        int g = (hexNumber & 0xF0) >> 4;
        int b = (hexNumber & 0xF);
        // Color.rgb expects a 2-hexit int in the range 0-255 for each color.
        // With just one hexit to work with, the bit is replicated 0x1 -> 0x11, 0x2 -> 0x22, etc.
        Color color = Color.rgb((r << 4) | r, (g << 4) | g, (b << 4) | b);
        return new Rectangle(PIXEL_SIZE, PIXEL_SIZE, color);
    }

    /* Check that the number of lines is a multiple of the number of pixels per sprite */
    private static void validateBitmapMemorySize(int length, int spriteSize) {
        if (length % (spriteSize * spriteSize) != 0) {
            throw new IllegalArgumentException(
                    "Number of lines in bitmap memory "
                            + length
                            + " is not a multiple of number of pixels per sprite "
                            + spriteSize * spriteSize);
        }
    }
}

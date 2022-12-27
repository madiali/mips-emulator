/**
 * DEPRECATED CODE, will delete after committing.
 * This code is proof of concept that we can generate working BMP from bmem.mem.
 * However, due to BMP format limitations, we represent the display as a 40x30 GridPane and sprites as 16x16 GridPanes of Rectangles.
 * This file is programmed with the assumption that each sprite in bmem is 16x16.
 * https://medium.com/sysf/bits-to-bitmaps-a-simple-walkthrough-of-bmp-image-format-765dc6857393
 */
package GUI;

import mips.BitmapMemory;
import mips.ProgramLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BMP {
    // Boilerplate header bits for all sprites
    // Note that fields that span more than 1 byte are represented in little-endian format
    // The only fields that should change for different sprite size are width (px) and height (px)
    // If this file was reformatted, consider looking at this array from a previous commit since its spacing and newlines are relevant
    private static final byte[] BMP_HEADER = new byte[] {
            0x42, 0x4D,
            0, 0, 0, 0,             // file size (B), set to 0 because JavaFX doesn't need this information
            0, 0,
            0, 0,
            0x36, 0, 0, 0,          // offset until actual pixel data (B)

            0x28, 0, 0, 0,
            0x10, 0, 0, 0,          // width (px), indices 18-21 (inclusive), must be a multiple of 16, default value 16
            0x10, 0, 0, 0,          // height (px), indices 22-25 (inclusive), must be a multiple of 16, default value 16
            0x01, 0,
            0x10, 0,                // bpp (bits per pixel)
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    @Deprecated
    public static void main(String[] args) throws IOException {
        ProgramLoader programLoader = new ProgramLoader(new File("src/test/TestProjects/Rubik/full_test.json"));
        BitmapMemory bmem = (BitmapMemory) programLoader.getMips().memDict.get(BitmapMemory.class).get(0);
        int[] bmemArr = bmem.getMemoryClone();

        assert bmemArr.length % 256 == 0;

        for (int sprite = 0; sprite < bmemArr.length / 256; sprite++) {
            File destination = new File("img/" + Integer.toHexString(sprite) + ".bmp");
            byte[] data = new byte[512];
            for (int row = 0; row < 16; row++) {
                for (int col = 0; col < 16; col++) {
                    int pixel = (16 * row + col);
                    int twoBytes = bigEndianToLittleEndian(bmemPixelToBMPPixel(bmemArr[sprite * 256 + pixel]));
                    int revPixel = 16 * (15 - row) + col;
                    data[2 * revPixel] = (byte) ((twoBytes & 0xFF00) >> 8);
                    data[2 * revPixel + 1] = (byte) (twoBytes & 0xFF);
                }
            }
            dataToFile(BMP_HEADER, destination, false);
            dataToFile(data, destination, true);
        }
    }

    /**
     * This method is unfinished.
     * Parse BitmapMemory to generate .bmp files representing the sprites.
     * They are named with decimal digits 0.bmp, 1.bmp... to make conversion to an Image[] easier.
     * @param spriteSize multiple of 16
     */
    @Deprecated
    public static void generateBMP(int spriteSize) throws IOException {
        ProgramLoader programLoader = new ProgramLoader(new File("src/test/TestProjects/Rubik/full_test.json"));
        BitmapMemory bmem = (BitmapMemory) programLoader.getMips().memDict.get(BitmapMemory.class).get(0);
        int[] bmemArr = bmem.getMemoryClone();

        assert bmemArr.length % (spriteSize * spriteSize) == 0;

        int numSprites = bmemArr.length / (spriteSize * spriteSize);
        for (int sprite = 0; sprite < numSprites; sprite++) {
            File destination = new File("img/" + sprite + ".bmp");
            byte[] data = new byte[2 * spriteSize * spriteSize];
            for (int row = 0; row < spriteSize; row++) {
                for (int col = 0; col < spriteSize; col++) {
                    int pixelIndex = spriteSize * row + col;
                    int reversedPixelIndex = spriteSize * (spriteSize - row) + col;
                    int pixelData = bmemPixelToBMPPixel(bmemArr[sprite * 256 + pixelIndex]);
                }
            }
        }

    }

    private static void dataToFile(byte[] data, File destination, boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(destination, append);
        fos.write(data);
    }

    /**
     * Convert a bmem 12-bit integer {r[3:0], g[3:0], b[3:0]} to the .BMP 16 bpp format {0, r[4:0], g[4:0], b[4:0]}.
     * Since each color is originally 4-bit but needs to become 5-bit, each color is shifted one additional time to maintain color saturation.
     * Color values will be incorrect but the ratio of r to g to b is maintained, so the colors appear mostly fine.
     * TODO: Come up with a better way to scale the colors equally.
     * NOTE: This pixel is returned in big-endian format.
     * @param pixel in bmem format
     * @return pixel in 16 bpp format
     */
    private static int bmemPixelToBMPPixel(int pixel) {
        int b = pixel & 0xF;
        int g = pixel & 0xF0;
        int r = pixel & 0xF00;
        return (b << 1) | (g << 2) | (r << 3);
    }

    /**
     * Convert two bytes in big-endian format to little-endian format.
     * @param twoBytes {a, b}
     * @return twoBytes {b, a}
     */
    @Deprecated
    private static int bigEndianToLittleEndian(int twoBytes) {
        int a = twoBytes & 0xFF00;
        int b = twoBytes & 0xFF;
        return (b << 8) | (a >> 8);
    }

}
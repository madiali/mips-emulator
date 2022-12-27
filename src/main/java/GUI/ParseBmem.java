/**
 * This code is essentially proof of concept that we can generate working BMP from bmem.mem, will make it more readable later.
 * Also, this is programmed with the assumption that each sprite in bmem is 16x16. TODO: Generalize later. Also make sure these render fine in GridPane.
 * https://medium.com/sysf/bits-to-bitmaps-a-simple-walkthrough-of-bmp-image-format-765dc6857393
 */
package GUI;

import mips.BitmapMemory;
import mips.ProgramLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParseBmem {
    // Boilerplate header bits for all sprites
    private static final byte[] bmp_header = new byte[] {
            0x42, 0x4D,
            0, 0, 0, 0,             // file size (B)
            0, 0,
            0, 0,
            0x36, 0, 0, 0,          // offset until actual pixel data (B)

            0x28, 0, 0, 0,
            0x10, 0, 0, 0,          // width (px)
            0x10, 0, 0, 0,          // height (px)
            0x01, 0,
            0x10, 0,                // bpp (bits per pixel)
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    };

    public static void main(String[] args) throws IOException {
        ProgramLoader programLoader = new ProgramLoader(new File("src/test/TestProjects/Rubik/full_test.json"));
        BitmapMemory bmem = (BitmapMemory) programLoader.getMips().memDict.get(BitmapMemory.class).get(0);

//        for (int i = 0; i < bmem.getSize() / bmem.getWordSize(); i++) {
//            System.out.println(i + ": " + bmem.getMemoryUnit(bmem.getWordSize() * i));
//        }
//        System.out.println(bmem.getSize() / bmem.getWordSize());

        // assert that #lines (pixels) in bmem is a multiple of 256 (16 px * 16 px)
        assert (bmem.getSize() / bmem.getWordSize()) % 256 == 0;
        for (int sprite = 0; sprite < bmem.getSize() / bmem.getWordSize() / 256; sprite++) {
            File destination = new File("img/" + Integer.toHexString(sprite) + ".bmp");
            byte[] data = new byte[512];
            for (int row = 0; row < 16; row++) {
                for (int col = 0; col < 16; col++) {
                    int pixel = (16 * row + col);
                    int twoBytes = bigEndianToLittleEndian(bmemPixelToBMPPixel(bmem.getMemoryUnit(bmem.getWordSize() * (sprite * 256 + pixel))));
                    int revPixel = 16 * (15 - row) + col;
                    data[2 * revPixel] = (byte) ((twoBytes & 0xFF00) >> 8);
                    data[2 * revPixel + 1] = (byte) (twoBytes & 0xFF);
                }
            }
            dataToFile(bmp_header, destination, false);
            dataToFile(data, destination, true);
        }
    }

    private static void dataToFile(byte[] data, File destination, boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(destination, append);
        fos.write(data);
    }

    /**
     * Convert a bmem 12-bit integer {r[3:0], g[3:0], b[3:0]} to the .BMP 16 bpp format {0, r[4:0], g[4:0], b[4:0]}.
     * However, this pixel is returned in big-endian format.
     * @param pixel in bmem format
     * @return pixel in 16 bpp format
     */
    private static int bmemPixelToBMPPixel(int pixel) {
        int b = pixel & 0xF;
        int g = pixel & 0xF0;
        int r = pixel & 0xF00;
        return b + (g << 1) + (r << 2);
    }

    /**
     * Convert two bytes in big-endian format to little-endian format.
     * @param twoBytes {a, b}
     * @return twoBytes {b, a}
     */
    private static int bigEndianToLittleEndian(int twoBytes) {
        int a = twoBytes & 0xFF00;
        int b = twoBytes & 0xFF;
        return (b << 8) + (a >> 8);
    }

}
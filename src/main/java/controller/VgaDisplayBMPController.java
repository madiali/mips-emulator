package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import mips.BitmapMemory;
import mips.Mips;
import mips.ScreenMemory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VgaDisplayBMPController {
    private final static int GRID_WIDTH = 40;
    private final static int GRID_HEIGHT = 30;
    private final static int SPRITE_LENGTH = 16;
    private final static int SPRITE_SIZE = SPRITE_LENGTH * SPRITE_LENGTH;

    private GridPane vgaDisplay;
    private ScreenMemory screenMemory;
    private BitmapMemory bitmapMemory;
    private int numSprites;
    private List<Image> spriteList;
//    private double pixelSize;
    private static final byte[] BMP_HEADER = new byte[]{
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

    public VgaDisplayBMPController(Mips mips, GridPane vgaDisplay) throws IOException {
        this.vgaDisplay = vgaDisplay;
        screenMemory = (ScreenMemory) mips.memDict.get(ScreenMemory.class).get(0);
        bitmapMemory = (BitmapMemory) mips.memDict.get(BitmapMemory.class).get(0);
//        pixelSize = Math.min(vgaDisplay.getWidth() / (GRID_WIDTH * SPRITE_LENGTH), vgaDisplay.getHeight() / (GRID_HEIGHT * SPRITE_LENGTH));
        numSprites = bitmapMemory.getSize() / bitmapMemory.getWordSize() / SPRITE_SIZE;

        generateBMP();
        this.spriteList = generateSpriteList();
        render();
    }

    private void generateBMP() throws IOException {
        for (int sprite = 0; sprite < numSprites; sprite++) {
            File destination = new File("img/" + sprite + ".bmp");
            byte[] data = new byte[2 * SPRITE_SIZE];
            for (int row = 0; row < SPRITE_LENGTH; row++) {
                for (int col = 0; col < SPRITE_LENGTH; col++) {
                    int pixelIndex = 16 * row + col;
                    int twoBytes = bigEndianToLittleEndian(bmemPixelToBMPPixel(bitmapMemory.getMemoryUnit(bitmapMemory.getWordSize() * (sprite * 256 + pixelIndex))));
                    int reversedPixelIndex = 16 * (15 - row) + col;
                    data[2 * reversedPixelIndex] = (byte) ((twoBytes & 0xFF00) >> 8);
                    data[2 * reversedPixelIndex + 1] = (byte) (twoBytes & 0xFF);
                }
            }
            dataToFile(BMP_HEADER, destination, false);
            dataToFile(data, destination, true);
        }
    }

    private List<Image> generateSpriteList() {
        List<Image> res = new ArrayList<>();
        for (int sprite = 0; sprite < numSprites; sprite++) {
            Image img = new Image("File:img/" + sprite + ".bmp");
            res.add(img);
        }
        return res;
    }

    public void render() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                int spriteNum = screenMemory.getMemoryUnit(screenMemory.getWordSize() * ((y * GRID_WIDTH) + x));
                ImageView sprite = new ImageView(spriteList.get(spriteNum));
                vgaDisplay.add(sprite, x, y);
            }
        }
    }

    private static void dataToFile(byte[] data, File destination, boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(destination, append);
        fos.write(data);
    }

    private static int bmemPixelToBMPPixel(int pixel) {
        int b = pixel & 0xF;
        int g = pixel & 0xF0;
        int r = pixel & 0xF00;
        return (b << 1) | (g << 2) | (r << 3);
    }

    private static int bigEndianToLittleEndian(int twoBytes) {
        int a = twoBytes & 0xFF00;
        int b = twoBytes & 0xFF;
        return (b << 8) | (a >> 8);
    }



}

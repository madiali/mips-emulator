/**
 * This file is programmed with the assumption that each sprite in bmem is 16x16. TODO: parameterize
 * (pixelSize), fix color. No need to scale images. If reformatting this file, consider pasting in
 * byte[] BMP_HEADER from a previous commit. The spacing and newlines are relevant.
 *
 * <p>For larger sprites, generateBMP() and bigEndianToLittleEndian would need to change, along with
 * a bunch of other stuff.
 *
 * <p>BMP explanation:
 * https://medium.com/sysf/bits-to-bitmaps-a-simple-walkthrough-of-bmp-image-format-765dc6857393
 *
 * <p>Original code doesn't access mips.memory (memory mapping) to get bmem and smem, so we don't
 * need to do so here either? TODO: look into this
 */
package GUI.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import mips.BitmapMemory;
import mips.Mips;
import mips.ScreenMemory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class VgaDisplayBMPController {
  private static final int GRID_WIDTH = 40;
  private static final int GRID_HEIGHT = 30;
  private static final int SPRITE_LENGTH = 16;
  private static final int SPRITE_SIZE = SPRITE_LENGTH * SPRITE_LENGTH;

  private static GridPane vgaDisplay;
  private static ScreenMemory screenMemory;
  private static BitmapMemory bitmapMemory;
  private static int numSprites;
  private static List<Image> spriteList;

  // Boilerplate header bits for all sprites
  // Note that fields that span more than 1 byte are represented in little-endian format
  // The only fields that should change for different sprite size are width (px) and height (px)
  // If this file is reformatted, please paste this array from a previous commit since its spacing
  // and newlines are relevant
  private static final byte[] BMP_HEADER = new byte[]{
          0x42, 0x4D,
          0, 0, 0, 0,             // file size (B), set to 0 because JavaFX doesn't need this information and with parameterization, file size is variable
          0, 0,
          0, 0,
          0x36, 0, 0, 0,          // offset until actual pixel data (B)

          0x28, 0, 0, 0,
          0x10, 0, 0, 0,          // width (px), indices 18-21 (inclusive), must be a multiple of 16, default value 16
          0x10, 0, 0, 0,          // height (px), indices 22-25 (inclusive), must be a multiple of 16, default value 16
          0x01, 0,
          0x10, 0,                // bpp (bits per pixel), currently set to 16 bpp (5 bits per color)
          0, 0, 0, 0,
          0, 0, 0, 0,
          0, 0, 0, 0,
          0, 0, 0, 0,
          0, 0, 0, 0,
          0, 0, 0, 0
  };

  /**
   * Create BMP files in img/ directory.
   * Create Image[] from BMP files.
   * Delete img/ directory.
   * Initialize VGA GridPane.
   * @param mips
   * @param vgaDisplay
   * @throws IOException
   */
  public VgaDisplayBMPController(Mips mips, GridPane vgaDisplay) throws IOException {
    VgaDisplayBMPController.vgaDisplay = vgaDisplay;
    screenMemory =
        (ScreenMemory)
            mips.getMemory().getMemUnits().stream()
                .filter(mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof ScreenMemory)
                .toList()
                .get(0)
                .getMemUnit();
    bitmapMemory = (BitmapMemory) mips.memDict.get(BitmapMemory.class).get(0);
    numSprites = bitmapMemory.getSize() / bitmapMemory.getWordSize() / SPRITE_SIZE;

    generateBMP();
    spriteList = generateSpriteList();
    deleteDir(new File("img"));
    initializeVGA();
  }

  /**
   * Create all ImageView's in the GridPane.
   */
  private static void initializeVGA() {
    for (int y = 0; y < GRID_HEIGHT; y++) {
      for (int x = 0; x < GRID_WIDTH; x++) {
        ImageView spriteContainer = new ImageView();
        vgaDisplay.add(spriteContainer, x, y);
      }
    }
    renderVGA();
  }

  /**
   * Render entire VGA display by using Screen Memory contents to index into spriteList.
   *
   * <p>Each ImageView object is unique, even if the Image (sprite) is the same, because GridPane
   * does not allow duplicate objects to be stored.
   *
   * <p>Updating the image of an ImageView instead of creating a new ImageView avoids a JavaFX
   * IllegalStateException related to updating on non-JavaFX application thread.
   */
  public static void renderVGA() {
    for (int y = 0; y < GRID_HEIGHT; y++) {
      for (int x = 0; x < GRID_WIDTH; x++) {
        int spriteIndex = (y * GRID_WIDTH) + x;
        ImageView spriteContainer = (ImageView) vgaDisplay.getChildren().get(spriteIndex);
        int spriteId =
            screenMemory.getMemoryUnit(screenMemory.getWordSize() * ((y * GRID_WIDTH) + x));
        spriteContainer.setImage(spriteList.get(spriteId));
      }
    }
  }

  /**
   * Overloaded version of renderVGA() that takes a specific sprite index. Since a single `sw`
   * instruction can modify at most a single element of smem, the index of smem that it modifies is
   * passed into this method.
   *
   * <p>Instead of iterating through the entire GridPane, set the Image of the spriteContainer at
   * that index of smem.
   *
   * <p>TODO: This method could be a one-liner, but then it'd be unreadable. But we could squeeze
   * out a bit more speed by making it a one-liner.
   *
   * @param spriteIndex
   */
  public static void renderVGA(int spriteIndex) {
    ImageView spriteContainer = (ImageView) vgaDisplay.getChildren().get(spriteIndex);
    int spriteId = screenMemory.getMemoryUnit(screenMemory.getWordSize() * spriteIndex);
    spriteContainer.setImage(spriteList.get(spriteId));
  }

  /**
   * Store contents of Bitmap Memory as .bmp images in the img/ directory. They are named 0.bmp,
   * 1.bmp, 2.bmp... regular decimal numbers.
   *
   * @throws IOException
   */
  private void generateBMP() throws IOException {
    File imgDir = new File("img");
    if (!imgDir.exists()) {
      imgDir.mkdirs();
    }
    for (int sprite = 0; sprite < numSprites; sprite++) {
      File destination = new File("img/" + sprite + ".bmp");
      byte[] data = new byte[2 * SPRITE_SIZE];
      for (int row = 0; row < SPRITE_LENGTH; row++) {
        for (int col = 0; col < SPRITE_LENGTH; col++) {
          // Hardcoded
          int pixelIndex = 16 * row + col;
          int twoBytes =
              bigEndianToLittleEndian(
                  bmemPixelToBMPPixel(
                      bitmapMemory.getMemoryUnit(
                          bitmapMemory.getWordSize() * (sprite * 256 + pixelIndex))));
          // Hardcoded
          int reversedPixelIndex = 16 * (15 - row) + col;
          data[2 * reversedPixelIndex] = (byte) ((twoBytes & 0xFF00) >> 8);
          data[2 * reversedPixelIndex + 1] = (byte) (twoBytes & 0xFF);
        }
      }
      dataToFile(BMP_HEADER, destination, false);
      dataToFile(data, destination, true);
    }
  }

  /**
   * @param data
   * @param destination
   * @param append true if data is appended to the end, false to overwrite existing contents, if any
   * @throws IOException
   */
  private static void dataToFile(byte[] data, File destination, boolean append) throws IOException {
    FileOutputStream fos = new FileOutputStream(destination, append);
    fos.write(data);
  }

  /**
   * Convert a bmem 12-bit integer {r[3:0], g[3:0], b[3:0]} to the .BMP 16 bpp format {0, r[4:0],
   * g[4:0], b[4:0]}. Since each color is originally 4-bit but needs to become 5-bit, each color is
   * shifted one additional time to maintain color saturation. LSB is incorrect but the ratio of r
   * to g to b is maintained, so the colors appear mostly fine. TODO: Come up with a better way to
   * scale the colors equally. NOTE: This pixel is returned in big-endian format, but BMP requires
   * little-endian. NOTE 2: This method does not need to change for bigger sprites. bmem.mem will
   * always store a single pixel as 3 hexits.
   *
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
   * Convert two bytes in big-endian format to little-endian format. THIS METHOD NEEDS TO CHANGE FOR
   * LARGER SPRITE SIZES.
   *
   * @param twoBytes {a, b}
   * @return twoBytes {b, a}
   */
  private static int bigEndianToLittleEndian(int twoBytes) {
    int a = twoBytes & 0xFF00;
    int b = twoBytes & 0xFF;
    return (b << 8) | (a >> 8);
  }

  /**
   * Assuming BMP files are stored in img/, put them all into an Image[].
   *
   * @return List<Image> of sprites
   */
  private List<Image> generateSpriteList() {
    List<Image> res = new ArrayList<>();
    for (int sprite = 0; sprite < numSprites; sprite++) {
      Image img = new Image("File:img/" + sprite + ".bmp");
      res.add(img);
    }
    return res;
  }

  /**
   * Delete a directory.
   *
   * @param file
   */
  private void deleteDir(File file) {
    File[] contents = file.listFiles();
    if (contents != null) {
      for (File f : contents) {
        if (!Files.isSymbolicLink(f.toPath())) {
          deleteDir(f);
        }
      }
    }
    file.delete();
  }
}

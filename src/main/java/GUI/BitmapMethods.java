package GUI;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
  public static List<GridPane> generateSpriteArray(String bitmapFile) throws IOException {
    List<String> hexValues = Files.readAllLines(Paths.get(bitmapFile));
    Iterator<String> it = hexValues.iterator();
    List<GridPane> spriteArray = new ArrayList<GridPane>();
    while (it.hasNext()) {
      GridPane sprite = new GridPane();
      for (int i = 0; i < SPRITE_SIZE; i++) {
        for (int j = 0; j < SPRITE_SIZE; j++) {
          Rectangle pixel = hexToRectangle(it.next());
          sprite.add(pixel, j, i);
        }
      }
      spriteArray.add(sprite);
    }
    return spriteArray;
  }

  /* Converts 3-digit hex string in bitmap memory file to a Rectangle object */
  public static Rectangle hexToRectangle(String hexString) {
    Color color = Color.web(hexString);
    return new Rectangle(PIXEL_SIZE, PIXEL_SIZE, color);
  }
}

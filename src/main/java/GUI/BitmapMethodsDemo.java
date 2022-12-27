package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import static GUI.BitmapMethods.generateSpriteArray;

public class BitmapMethodsDemo extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Sprite Demo");
    List<GridPane> spriteArray = generateSpriteArray("src/test/TestProjects/CatsAndDogs/bmem.mem");
    GridPane grid = new GridPane();
    for (int i = 0; i < 16; i++) {
      grid.add(spriteArray.get(i), i, 0);
    }
    Scene scene = new Scene(grid);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

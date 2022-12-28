package GUI.misc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mips.BitmapMemory;
import mips.ProgramLoader;

import java.io.File;
import java.util.List;

import static GUI.display.BitmapMethods.generateSpriteArray;

public class BitmapMethodsDemo extends Application {

    /**
     * Test for BitmapMethods.
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Sprite Demo");
        ProgramLoader programLoader = new ProgramLoader(new File("src/test/TestProjects/Rubik/full_test.json"));
        BitmapMemory bmem = (BitmapMemory) programLoader.getMips().memDict.get(BitmapMemory.class).get(0);

        List<GridPane> spriteArray = generateSpriteArray("src/test/TestProjects/CatsAndDogs/bmem.mem");
        List<GridPane> spriteArray2 = generateSpriteArray(bmem);

        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();
        for (int i = 0; i < 16; i++) {
            grid.add(spriteArray.get(i), i, 0);
            grid2.add(spriteArray2.get(i), i, 0);
        }
        Scene scene = new Scene(grid);
//    Scene scene = new Scene(grid2);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

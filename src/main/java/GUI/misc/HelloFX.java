package GUI.misc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hi :)");
        // Hello there (:
        GridPane grid = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Image img = new Image("File:img/" + Integer.toHexString(6 * i + j) + ".bmp");
                ImageView ogImg = new ImageView(img);
                grid.add(ogImg, j, i);

                ImageView bigImg = new ImageView(img);
                bigImg.setFitHeight(32);
                bigImg.setFitWidth(32);
                bigImg.setPreserveRatio(true);
                grid.add(bigImg, j, i + 6);

                ImageView biggerImg = new ImageView(img);
                biggerImg.setFitHeight(64);
                biggerImg.setFitWidth(64);
                biggerImg.setPreserveRatio(true);
                grid.add(biggerImg, j, i + 12);

                ImageView biggestImg = new ImageView(img);
                biggestImg.setFitHeight(128);
                biggestImg.setFitWidth(128);
                biggestImg.setPreserveRatio(true);
                grid.add(biggestImg, j, i + 18);

            }
        }
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
}

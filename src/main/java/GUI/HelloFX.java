package GUI;

import javafx.application.Application;
import javafx.scene.*;
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
        GridPane grid = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Image img = new Image("File:img/" + Integer.toHexString(5 * i + j) + ".bmp");
                grid.add(new ImageView(img), j, i);
            }

        }
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
}

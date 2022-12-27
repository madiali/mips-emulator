package GUI;

import javafx.application.Application;
import javafx.scene.*;
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
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
}

package GUI.misc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccelerometerDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize stage
        stage.setTitle("Accelerometer Demo");
        stage.setMaximized(true);

        // Initialize components
        GUI.Accelerometer ac = new GUI.Accelerometer();
        VBox layout = new VBox();
        layout.getChildren().add(ac.render());

        // Initialize scene
        Scene scene = new Scene(layout);
        stage.setScene(scene);

        // Show stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
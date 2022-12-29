package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppLauncher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Initialize stage
        stage.setTitle("MIPS Emulator");
        stage.setMaximized(true);
        stage.setResizable(false);

        // Model

        // View
        View view = new View();

        // Controller

        // Initialize scene
        Scene scene = new Scene(view.render());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

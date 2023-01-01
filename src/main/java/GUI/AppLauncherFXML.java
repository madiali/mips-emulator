package GUI;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class AppLauncherFXML extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setStage(stage);

        stage.setTitle("MIPS Emulator");
//        stage.setFullScreen(true);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        // Set listeners for key presses here (must have access to scene), then forward to MainController
        scene.setOnKeyPressed(event -> {
            KeyCode keycode = event.getCode();
            controller.handleOnKeyDown(keycode);
        });
        scene.setOnKeyReleased(event -> {
            KeyCode keycode = event.getCode();
            controller.handleOnKeyUp(keycode);
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

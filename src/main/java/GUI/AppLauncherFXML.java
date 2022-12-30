package GUI;

import controller.MipsController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class AppLauncherFXML extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
        Parent root = loader.load();
        // I suspect that if we construct the controller ourselves using new MipsController(), then FXML methods might not be linked to that controller instance?
        MipsController controller = loader.getController();
        controller.setStage(stage);

        stage.setTitle("MIPS Emulator");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

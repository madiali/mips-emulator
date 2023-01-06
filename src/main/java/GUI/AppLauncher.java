package GUI;

import GUI.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
    Parent root = loader.load();

    MainController controller = loader.getController();

    stage.setTitle(controller.getMips().getName());
    stage.setMaximized(true);
    stage.setResizable(false);
    Scene scene = new Scene(root);

    scene.setOnKeyPressed(event -> controller.handleOnKeyDown(event.getCode()));
    scene.setOnKeyReleased(event -> controller.handleOnKeyUp(event.getCode()));

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

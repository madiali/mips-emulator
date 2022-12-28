package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class View extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize stage
        stage.setTitle("MIPS Emulator");
        stage.setMaximized(true);

        // Initialize layout
        BorderPane layout = new BorderPane();

        // Layout top (menu bar)
        ViewMenu viewMenu = new ViewMenu();
        layout.setTop(viewMenu.render());

        // Layout LHS center (screen and accelerometer)
        BorderPane main = new BorderPane();
        BorderPane lhs = new BorderPane();
        Accelerometer accelerometer = new Accelerometer();
        lhs.setBottom(accelerometer.render());
        Screen screen = new Screen();
        lhs.setCenter(screen.render());
        main.setCenter(lhs);

        // Layout RHS center (register / memory)
        Rectangle tempRectangle = new Rectangle(500, 700, Color.GRAY);
        Insets rectangleInset = new Insets(10, 10, 10, 10);
        StackPane rectangleStackPane = new StackPane();
        rectangleStackPane.getChildren().add(tempRectangle);
        rectangleStackPane.setPadding(rectangleInset);
        main.setRight(tempRectangle);
        layout.setCenter(main);

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
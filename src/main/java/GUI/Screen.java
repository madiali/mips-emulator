package GUI;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class Screen implements FXComponent {
    private GridPane layout;

    public Screen() {
    }

    @Override
    public Parent render() {
        layout = new GridPane();

        // Style screen
        layout.setStyle("-fx-border: 2px solid; -fx-border-color: black;");
        layout.setMaxSize(40 * 16, 30 * 16);

        // Generate screen

        return layout;
    }
}

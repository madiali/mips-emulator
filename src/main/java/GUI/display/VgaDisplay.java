package GUI.display;

import GUI.FXComponent;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class VgaDisplay implements FXComponent {
    public final static int WIDTH = 40 * 16;
    public final static int HEIGHT = 30 * 16;

    private GridPane layout;

    public VgaDisplay() {
    }

    @Override
    public Parent render() {
        layout = new GridPane();

        // Style screen
        layout.setStyle("-fx-border: 2px solid; -fx-border-color: black;");
        layout.setMaxSize(WIDTH, HEIGHT);

        return layout;
    }
}

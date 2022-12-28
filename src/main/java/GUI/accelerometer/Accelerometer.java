package GUI.accelerometer;

import GUI.FXComponent;
import GUI.display.VgaDisplay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Accelerometer implements FXComponent {
    public final static int SLIDER_MIN = 0;
    public final static int SLIDER_MAX = 511;
    public final static int SLIDER_DEFAULT = 255;

    private VBox layout;

    public Accelerometer() {
    }

    @Override
    public Parent render() {
        layout = new VBox();
        layout.setPadding(new Insets(10, 10, 100, 10));
        layout.setAlignment(Pos.CENTER);

        // x Slider
        HBox xHBox = (new XSlider()).render();

        // y Slider
        HBox yHBox = (new YSlider()).render();

        // Reset
        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(VgaDisplay.WIDTH);

        layout.getChildren().addAll(xHBox, yHBox, resetButton);

        return layout;
    }
}
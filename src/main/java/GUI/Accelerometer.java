package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Accelerometer implements FXComponent {
    private VBox layout;

    public Accelerometer() {
    }

    @Override
    public Parent render() {
        layout = new VBox();
        Insets inset = new Insets(10, 10, 100, 10);
        layout.setPadding(inset);

        // x Slider
        HBox xHBox = new HBox();
        Label xLabel = new Label("X: ");
        xHBox.getChildren().add(xLabel);
        Slider xSlider = new Slider();
        xSlider.setMin(0);
        xSlider.setMax(511);
        xSlider.setValue(255);
        xSlider.setBlockIncrement(1);
        xSlider.setPrefWidth(40 * 16);
        xHBox.getChildren().add(xSlider);
        xHBox.setAlignment(Pos.CENTER);
        layout.getChildren().add(xHBox);

        // y Slider
        HBox yHBox = new HBox();
        Label yLabel = new Label("Y: ");
        yHBox.getChildren().add(yLabel);
        Slider ySlider = new Slider();
        ySlider.setMin(0);
        ySlider.setMax(511);
        ySlider.setValue(255);
        ySlider.setBlockIncrement(1);
        ySlider.setPrefWidth(40 * 16);
        yHBox.getChildren().add(ySlider);
        yHBox.setAlignment(Pos.CENTER);
        layout.getChildren().add(yHBox);

        // Reset
        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(40 * 16);
        layout.getChildren().add(resetButton);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }
}
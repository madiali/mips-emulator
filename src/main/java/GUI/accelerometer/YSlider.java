package GUI.accelerometer;

import GUI.display.VgaDisplay;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class YSlider {
    private HBox layout;
    private Slider ySlider;

    public YSlider() {
        layout = new HBox();
        layout.setAlignment(Pos.CENTER);

        // Label
        Label yLabel = new Label("Y: ");

        //Slider
        ySlider = new Slider();
        ySlider.setPrefWidth(VgaDisplay.WIDTH);
        ySlider.setMin(Accelerometer.SLIDER_MIN);
        ySlider.setMax(Accelerometer.SLIDER_MAX);
        ySlider.setValue(Accelerometer.SLIDER_DEFAULT);

        layout.getChildren().addAll(yLabel, ySlider);
    }

    public HBox render() {
        return layout;
    }
}

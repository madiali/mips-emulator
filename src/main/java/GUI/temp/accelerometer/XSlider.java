package GUI.temp.accelerometer;

import GUI.temp.display.VgaDisplay;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class XSlider {
    private HBox layout;
    private Slider xSlider;

    public XSlider() {
        layout = new HBox();
        layout.setAlignment(Pos.CENTER);

        // Label
        Label xLabel = new Label("X: ");

        //Slider
        xSlider = new Slider();
        xSlider.setPrefWidth(VgaDisplay.WIDTH);
        xSlider.setMin(Accelerometer.SLIDER_MIN);
        xSlider.setMax(Accelerometer.SLIDER_MAX);
        xSlider.setValue(Accelerometer.SLIDER_DEFAULT);

        layout.getChildren().addAll(xLabel, xSlider);
    }

    public HBox render() {
        return layout;
    }
}
package controller;

import javafx.scene.control.Slider;
import mips.AccelerometerX;
import mips.AccelerometerY;
import mips.Mips;

public class AccelerometerController implements DebuggerView {
    private AccelerometerX accelerometerX;
    private AccelerometerY accelerometerY;
    private Slider xSlider;
    private Slider ySlider;
    private final double defaultAccelValue = 255;

    /* The AccelerometerController might need to take mips as a parameter to get accelerometerX and accelerometerY from it? */
    public AccelerometerController(Mips mips) {
    }

    public void setXSlider(Slider xSlider) {
        this.xSlider = xSlider;
    }

    public void setYSlider(Slider ySlider) {
        this.ySlider = ySlider;
    }

    public void handleResetButton() {
        xSlider.setValue(defaultAccelValue);
        ySlider.setValue(defaultAccelValue);
    }

    @Override
    public void refreshDisplay() {

    }

    @Override
    public void close() {

    }
}

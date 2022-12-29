package controller;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mips.*;

public class AccelerometerController implements DebuggerView {
  private AccelerometerX accelerometerX;
  private AccelerometerY accelerometerY;
  private Slider xSlider;
  private Slider ySlider;
  private Label xLabel;
  private Label yLabel;
  private Button resetButton;
  private final double defaultAccelValue = 255;

  /* The AccelerometerController might need to take mips as a parameter to get accelerometerX and accelerometerY from the same Mips as the one in MipsController? */
  public AccelerometerController(Mips mips, Slider xSlider, Slider ySlider, Label xLabel, Label yLabel, Button resetButton) {
    this.xSlider = xSlider;
    this.ySlider = ySlider;
    this.xLabel = xLabel;
    this.yLabel = yLabel;
    this.resetButton = resetButton;
    Accelerometer mappedAccelerometer = null;
    for (MappedMemoryUnit mappedMemoryUnit : mips.getMemory().getMemUnits()) {
      if (mappedMemoryUnit.getMemUnit() instanceof Accelerometer) {
        mappedAccelerometer = (Accelerometer) mappedMemoryUnit.getMemUnit();
        break;
      }
    }
    if (mappedAccelerometer != null) {
      this.accelerometerX = mappedAccelerometer.getAccelX();
      this.accelerometerY = mappedAccelerometer.getAccelY();
      accelerometerX.setXValue((int) defaultAccelValue);
      accelerometerY.setYValue((int) defaultAccelValue);
    } else {
      close();
    }
  }

  public void handleXSliderDrag() {
    accelerometerX.setXValue((int) xSlider.getValue());
  }

  public void handleYSliderDrag() {
    accelerometerY.setYValue((int) ySlider.getValue());
  }

  public void handleResetButton() {
    xSlider.setValue(defaultAccelValue);
    ySlider.setValue(defaultAccelValue);
    accelerometerX.setXValue((int) defaultAccelValue);
    accelerometerY.setYValue((int) defaultAccelValue);
  }

  /** Unnecessary to call this, sliders update on screen automatically. */
  @Deprecated
  @Override
  public void refreshDisplay() {}

  /**
   * When Accelerometer is not in Memory Mapper, then remove Sliders, labels, and Reset button.
   */
  @Override
  public void close() {
        HBox parent = (HBox) this.xSlider.getParent();
        parent.getChildren().remove(xSlider);
        parent = (HBox) this.ySlider.getParent();
        parent.getChildren().remove(ySlider);

        parent = (HBox) this.xLabel.getParent();
        parent.getChildren().remove(xLabel);

        this.yLabel.setText("There would be sliders here for controlling Accelerometer, but you have not memory mapped it in your JSON!");

        VBox parent2 = (VBox) this.resetButton.getParent();
        parent2.getChildren().remove(resetButton);
  }
}

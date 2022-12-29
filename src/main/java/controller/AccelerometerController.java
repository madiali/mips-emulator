package controller;

import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mips.*;

public class AccelerometerController implements DebuggerView {
  private AccelerometerX accelerometerX;
  private AccelerometerY accelerometerY;
  private Slider xSlider;
  private Slider ySlider;
  private final double defaultAccelValue = 255;

  /* The AccelerometerController might need to take mips as a parameter to get accelerometerX and accelerometerY from the same Mips as the one in MipsController? */
  public AccelerometerController(Mips mips) {
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
    // mappedAccelerometer will be null if Accelerometer is not in the JSON. We can add logic to
    // handle this case later, which could include removing the Slider components since any action
    // on a slider with null Accelerometer will throw Exception (which doesn't seem to crash the
    // program?)
  }

  public void setXSlider(Slider xSlider) {
    this.xSlider = xSlider;
  }

  public void setYSlider(Slider ySlider) {
    this.ySlider = ySlider;
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
   * Call this to remove Sliders since Accelerometer is not mapped. However, this code fails when
   * Accelerometer is not mapped, which somehow causes xSlider to be null???
   */
  @Override
  public void close() {
//        HBox parent = (HBox) MipsController.xSlider.getParent();
//        parent.getChildren().remove(xSlider);
//        parent = (HBox) MipsController.ySlider.getParent();
//        parent.getChildren().remove(ySlider);
  }
}

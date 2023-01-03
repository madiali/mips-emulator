package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mips.*;

public class AccelerometerController {
  private AccelerometerX accelerometerX;
  private AccelerometerY accelerometerY;
  private Slider xSlider;
  private Slider ySlider;
  private Label xLabel;
  private Label yLabel;
  private Button resetButton;
  private final double defaultAccelValue = 255;

  /**
   * The AccelerometerController might have to take mips as a parameter to get accelerometerX and
   * accelerometerY from the same Mips as the one in MipsController?
   *
   * <p>Also, the logic here mostly follows that of ExtractAccelerometer in MainWindow.cs. We have
   * different edge case behavior, though.
   *
   * <p>TODO: Check for AccelerometerX and AccelerometerY if Accelerometer doesn't exist.
   */
  public AccelerometerController(
      Mips mips, Slider xSlider, Slider ySlider, Label xLabel, Label yLabel, Button resetButton) {
    this.xSlider = xSlider;
    this.ySlider = ySlider;
    this.xLabel = xLabel;
    this.yLabel = yLabel;
    this.resetButton = resetButton;
    Accelerometer mappedAccelerometer = null;
    // This code is very similar to the OG code in MainWindow.cs extractAccelerometer. Messy
    // but it'd be a for loop otherwise. If there's a way around using toList(), then the try catch
    // could be removed.
    try {
      mappedAccelerometer =
          (Accelerometer)
              mips.getMemory().getMemUnits().stream()
                  .filter(
                      mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof Accelerometer)
                  .toList()
                  .get(0)
                  .getMemUnit();
    } catch (ArrayIndexOutOfBoundsException aioobe) {
      // If Accelerometer isn't memory mapped (has startAddr or bitmask) in JSON, then the toList() is empty and .get(0) causes
      // IndexOutOfBoundsException. mappedAccelerometer remains null.
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


  /**
   * When Accelerometer is not in Memory Mapper, then remove Sliders, labels, and Reset button.
   * Otherwise, Accelerometer is null and dragging Sliders throws Exception.
   *
   * <p>Note: We could let the parent variables be of generic type Parent, and then this code would
   * not need to be modified if the FXML layout changes. However, Parent does not have access to
   * getChildren(), only getChildrenUnmodifiable(). If we change the layout, this wouldn't be hard
   * to recode.
   */
  public void close() {
    HBox parent = (HBox) this.xSlider.getParent();
    parent.getChildren().remove(xSlider);
    parent = (HBox) this.ySlider.getParent();
    parent.getChildren().remove(ySlider);

    parent = (HBox) this.xLabel.getParent();
    parent.getChildren().remove(xLabel);

    this.yLabel.setText(
        "There would be sliders here for controlling Accelerometer, but you have not memory mapped it in your JSON!");

    VBox parent2 = (VBox) this.resetButton.getParent();
    parent2.getChildren().remove(resetButton);
  }
}

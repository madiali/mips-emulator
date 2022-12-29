// From original code, but unsure whether this is needed in ours?
// For example, AccelerometerControl does not need a refreshDisplay() method, though it may need a
// close() for when Accelerometer is not mapped and we should remove the sliders.
package controller;

public interface DebuggerView {
  void refreshDisplay();

  void close();
}

## Skipped/incomplete files

These are files that we're skipping for now and will come back to later, as time allows. There's also files that are
incomplete as they depend on other files.

* `Sound`
* `SoundModule`
* `SoundWaveGenerator`
* `AccelerometerController`
    * Handle the edge case when the accelerometer isn't in the JSON; check for AccelerometerX and AccelerometerY. After
      that, then we can assume they don't use Accelerometer, so the sliders can be closed.
* `VgaDisplayBMPController`
    * Handle parameteriztion, 32x32 and 64x64 sprites
    * Put BMP methods into a separate file
* `KeyboardController` and `AccelerometerController`, etc.
    * Should probably check for when these aren't memory mapped but are still in the JSON just in case?
    * ProjectA specification always memory maps keyboard and accelerometer though. This task isn't too high on the ol'
      priority queue
    * `KeyboardController` maps LEFT and RIGHT arrow keys, but JavaFX doesn't automatically call the handler methods for
      those buttons specifically because it instead changes the value of the GUI accel sliders. This needs to be put in
      the README later, would hate for someone to need to debug that when it's really a program limitation. We could
      have dedicated LEFT and RIGHT buttons on the GUI to work around this.
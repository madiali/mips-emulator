/**
 * https://www.youtube.com/watch?v=r_MbozD32eo Multithreading in Java Explained in 10 Minutes We can
 * extend Thread or implement Runnable here. Either is fine.
 *
 * <p>The OG program and our program need to use Thread for MIPS program execution because all the
 * event handler and GUI code needs to run in the background concurrently.
 *
 * <p>https://stackoverflow.com/questions/29449297/java-lang-illegalstateexception-not-on-fx-application-thread-currentthread-t
 * tl;dr We will get an Illegal State Exception when updating UI on some thread (like this one, sad)
 * other than the JavaFX Application thread. I think ExecuteAll has to be a thread because the
 * listener code in MainController and other places need to run concurrently. We need to find a
 * workaround.
 * https://community.oracle.com/tech/developers/discussion/2592236/javafx-binding-throws-illegal-state-exception-not-on-fx-application-thread
 * The above page suggests using
 * https://docs.oracle.com/javafx/2/api/javafx/application/Platform.html#runLater(java.lang.Runnable)
 */
package controller;

import mips.MappedMemoryUnit;
import mips.Mips;
import mips.ScreenMemory;
import mips.instructions.IType.SwInstruction;

public class ExecuteAll implements Runnable {
  private Mips mips;

  public ExecuteAll(Mips mips) {
    this.mips = mips;
  }

  /**
   * renderVGA runs only when a `sw` Instruction is executed. renderRegisterTable and
   * renderDataMemoryTable run when the pause button is clicked. Commented out for now. Since run()
   * loops based on isExecuting, that logic should probably be handled in handlePause(). There is
   * currently some code here for tracking time.
   *
   * <p>Results of the commented-out print statements (confirming that they work correctly)
   *
   * <p>Screen memory start: 0x10020000 Screen memory end: 0x100212BF Gap between the above 2
   * addresses: 4799
   */
  @Override
  public void run() {
    boolean swInstructionExecuted;
    boolean swToScreenMemory;
    int instructionsExecuted = 0;
    MappedMemoryUnit screenMemory =
        mips.getMemory().getMemUnits().stream()
            .filter(mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof ScreenMemory)
            .toList()
            .get(0);
    //    System.out.println("Screen memory start: " + String.format("0x%08X",
    // screenMemory.getStartAddr()));
    //    System.out.println("Screen memory end: " + String.format("0x%08X",
    // screenMemory.getEndAddr()));
    //    System.out.println("Gap between the above 2 addresses: " + (screenMemory.getEndAddr() -
    // screenMemory.getStartAddr()));
    long start = System.currentTimeMillis() / 1000;
    for (; MainController.getIsExecuting(); ) {
      instructionsExecuted++;
      swInstructionExecuted =
          mips.getInstrMem().getInstruction(mips.getPC()) instanceof SwInstruction;
      mips.executeNext();
      //      RegistersController.renderRegisterTable();
      //      DataMemoryController.renderDataMemoryTable();
      if (swInstructionExecuted) {
        VgaDisplayBMPController.renderVGA();
      }
    }
    long end = System.currentTimeMillis() / 1000;
    long delta = end - start;
    System.out.println("Time: " + delta + "s");
    System.out.println("Mips instructions executed: " + instructionsExecuted);
    System.out.println("Clock speed: " + instructionsExecuted / 1000000.0 / delta + " MHz");
  }
}

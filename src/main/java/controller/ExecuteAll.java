/**
 * https://www.youtube.com/watch?v=r_MbozD32eo Multithreading in Java Explained in 10 Minutes We can
 * extend Thread or implement Runnable here. Either is fine.
 *
 * <p>The OG program and our program need to use Thread for MIPS program execution because all the
 * event handler and GUI code needs to run in the background concurrently.
 *
 * https://stackoverflow.com/questions/29449297/java-lang-illegalstateexception-not-on-fx-application-thread-currentthread-t
 * tl;dr We will get an Illegal State Exception when updating UI on some thread (like this one, sad) other than the JavaFX Application thread.
 * I think ExecuteAll has to be a thread because the listener code in MainController and other places need to run concurrently. We need to find a workaround.
 * https://community.oracle.com/tech/developers/discussion/2592236/javafx-binding-throws-illegal-state-exception-not-on-fx-application-thread
 * The above page suggests using https://docs.oracle.com/javafx/2/api/javafx/application/Platform.html#runLater(java.lang.Runnable)
 */
package controller;

import mips.Mips;

public class ExecuteAll implements Runnable {
  private Mips mips;

  public ExecuteAll(Mips mips) {
    this.mips = mips;
  }

  /**
   * There are currently some print statements for debugging. For Rubik, I get the same
   * UnmappedAddressException (same address even) as the one from the OG emulator. I guess that's good?
   */
  @Override
  public void run() {
    for (; MainController.getIsExecuting() ;) {
//      System.out.print(mips.getInstrMem().getInstruction(mips.getPC()).toString() + " | sp: ");
      mips.executeNext();
      RegistersController.renderRegisterTable();
      DataMemoryController.renderDataMemoryTable();
      // For some reason, the above 2 don't error, whereas the below will error.
      VgaDisplayBMPController.renderVGA();
//      System.out.print(Integer.toHexString(mips.getReg().getRegister(29)) + "\n");
    }
  }
}

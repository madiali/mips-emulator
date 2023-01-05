/**
 * https://www.youtube.com/watch?v=r_MbozD32eo Multithreading in Java Explained in 10 Minutes. We
 * can extend Thread or implement Runnable here. Either is fine.
 *
 * <p>The OG program and our program need to use Thread for MIPS program execution because the event
 * handler and GUI code needs to run in the background concurrently.
 *
 * <p>https://stackoverflow.com/questions/29449297/java-lang-illegalstateexception-not-on-fx-application-thread-currentthread-t
 * We solved this by updating the ImageView in VgaDisplayBMPController instead of adding new
 * ImageView's.
 */
package controller;

import controller.DataMemoryController;
import controller.MainController;
import controller.RegistersController;
import controller.VgaDisplayBMPController;
import mips.*;
import mips.instructions.IType.BeqInstruction;
import mips.instructions.IType.BneInstruction;
import mips.instructions.IType.ITypeInstruction;
import mips.instructions.IType.SwInstruction;
import mips.instructions.Instruction;
import mips.instructions.JType.JInstruction;
import mips.instructions.RType.JrInstruction;
import mips.instructions.RType.RTypeInstruction;

public class ExecuteAll implements Runnable {
  private Mips mips;

  public ExecuteAll(Mips mips) {
    this.mips = mips;
  }

  /**
   * renderVGA(spriteIndex) runs only when a SwInstruction is executed AND that SwInstruction's
   * target address is in the bounds of mapped Screen Memory. renderRegisterTable and
   * renderDataMemoryTable run when the pause button is clicked. Commented out for now, we need to
   * speed those up. There's some code here for tracking time. For now, stats are printed out when
   * the pause or exit buttons are clicked.
   *
   * <p>The code in this method optimizes for speed instead of readability, so we explain how it
   * works here in the docstring:
   *
   * <p>The lines before the for loop get some necessary variables.
   *
   * <p>The for loop goes while boolean isExecuting is true. It always executes the nextInstruction
   * (at current PC value) and determines whether it's a SwInstruction.
   *
   * <p>If so, then it computes the target address of the SwInstruction to determine whether it's
   * storing to ScreenMemory.
   *
   * <p>If so, then it computes the spriteIndex of Screen Memory by (targetAddr - startAddr) / 4.
   * spriteIndex, a number between 0 and 1199 (for 16x16 sprites, would change for bigger sprites),
   * is passed to the overloaded renderVGA method. With a specific spriteIndex, renderVGA updates
   * only one ImageView instead of looping through the entire GridPane.
   */
  @Override
  public void run() {
    long instructionsExecuted = 0;
    Registers reg = mips.getReg();
    MappedMemoryUnit screenMemory =
        mips.getMemory().getMemUnits().stream()
            .filter(mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof ScreenMemory)
            .toList()
            .get(0);
    int smemStartAddr = screenMemory.getStartAddr();
    int smemEndAddr = screenMemory.getEndAddr();
    MappedMemoryUnit dataMemory =
        mips.getMemory().getMemUnits().stream()
            .filter(mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof DataMemory)
            .toList()
            .get(0);
    int dmemStartAddr = dataMemory.getStartAddr();
    int dmemEndAddr = dataMemory.getEndAddr();

    long start = System.currentTimeMillis() / 1000;

    /**
     * This code optimizes for speed. Might be difficult to read ig. Some explanations in docstring
     * of method.
     */
    for (; MainController.getIsExecuting(); instructionsExecuted++) {
      Instruction nextInstruction = mips.getInstrMem().getInstruction(mips.getPC());
      mips.executeNext();
      // Rendering logic
      if (nextInstruction instanceof SwInstruction) {
        SwInstruction swInstruction = (SwInstruction) nextInstruction;
        int targetAddr =
            reg.getRegister(swInstruction.getS()) + signExtend(swInstruction.getImmediate());
        // Display rendering condition
        if (smemStartAddr <= targetAddr && targetAddr <= smemEndAddr) {
          VgaDisplayBMPController.renderVGA((targetAddr - smemStartAddr) >> 2);
        }
        // TODO: Data memory rendering condition
        // Note: Something is wrong with this if statement. Code within never executes.
        else if (dmemStartAddr <= targetAddr && targetAddr <= dmemEndAddr) {
          DataMemoryController.renderDataMemoryTable();
        }
      }
      // Registers rendering condition
      else if (!(nextInstruction instanceof BeqInstruction)
          && !(nextInstruction instanceof BneInstruction)
          && !(nextInstruction instanceof JInstruction)
          && !(nextInstruction instanceof JrInstruction)) {
        if (nextInstruction instanceof ITypeInstruction) {
          RegistersController.renderRegisterTable(((ITypeInstruction) nextInstruction).getT());
        } else if (nextInstruction instanceof RTypeInstruction) {
          RegistersController.renderRegisterTable(((RTypeInstruction) nextInstruction).getD());
        }
      }
    }

    long delta = System.currentTimeMillis() / 1000 - start;
    System.out.println("Time: " + delta + "s");
    System.out.println("Mips instructions executed: " + instructionsExecuted);
    System.out.println("Clock speed (avg): " + instructionsExecuted / 1000000.0 / delta + " MHz");
  }

  /**
   * Taken from mips.instructions.IType. Not importing from there since it's protected there, and
   * also putting it here shaves off some time. Made it a one-liner to shave off time.
   *
   * @param immediate
   * @return sign-extended immediate
   */
  private static int signExtend(int immediate) {
    return (((immediate >> 15) & 0b1) == 0) ? immediate : (immediate | 0xFFFF0000);
  }
}

package controller;

import javafx.scene.control.MenuItem;
import mips.ScreenMemory;

public class MenuController {
  private MenuItem open;
  private MenuItem exit;
  private MenuItem go;
  private MenuItem pause;
  private MenuItem stepForward;
  private VgaDisplayBMPController vgaDisplayBMPController;

  public MenuController(
      MenuItem open,
      MenuItem exit,
      MenuItem go,
      MenuItem pause,
      MenuItem stepForward,
      VgaDisplayBMPController vgaDisplayBMPController) {
    this.open = open;
    this.exit = exit;
    this.go = go;
    this.pause = pause;
    this.stepForward = stepForward;
    this.vgaDisplayBMPController = vgaDisplayBMPController;
  }

  // There is no handleOpen() here. Since Controllers are instantiated in handleOpen, handleOpen
  // needs to be handled in MipsController

  public void handleExit() {}

  public void handleGo() {}

  public void handlePause() {}

  // Purely for testing render speed, replace later with actual implementation
  public void handleStepForward() {
    ScreenMemory smem = vgaDisplayBMPController.screenMemory;
    for (int i = 0; i < smem.getSize(); i += smem.getWordSize()) {
      int newValue = smem.getMemoryUnit(i) == 30 ? 0 : smem.getMemoryUnit(i) + 1;
      smem.setMemoryUnit(i, newValue);
    }
    vgaDisplayBMPController.renderVGA();
  }

}

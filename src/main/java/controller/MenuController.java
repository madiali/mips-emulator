package controller;

import javafx.scene.control.MenuItem;
import mips.Mips;

public class MenuController {
  private MenuItem open;
  private MenuItem exit;
  private MenuItem go;
  private MenuItem pause;
  private MenuItem stepForward;

  public MenuController(
          Mips mips, MenuItem open, MenuItem exit, MenuItem go, MenuItem pause, MenuItem stepForward) {
    this.open = open;
    this.exit = exit;
    this.go = go;
    this.pause = pause;
    this.stepForward = stepForward;
  }

  // There is no handleOpen() here. Since Controllers are instantiated in handleOpen, handleOpen
  // needs to be handled in MipsController

  public void handleExit() {}

  public void handleGo() {}

  public void handlePause() {}

  public void handleStepForward() {}
}

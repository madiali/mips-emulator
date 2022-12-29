package controller;

import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mips.Mips;
import mips.ProgramLoader;
import mips.Registers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* Controller may need to implement Initializable for FXML, unsure */
public class MipsController {
    private Mips mips;
    // FileChooser uses Stage, this might be needed?
    private Stage stage;

    public MipsController(Mips mips) {
        if (mips == null) {
            throw new IllegalArgumentException("Mips is null");
        }
        this.mips = mips;
    }

    public MipsController() {

    }

    public int getPC() {
        return mips.getPC();
    }

    public String getName() {
        return mips.getName();
    }

    public float getClockSpeed() {
        return mips.getClockSpeed();
    }

    public void setClockSpeed(float newClockSpeed) {
        mips.setClockSpeed(newClockSpeed);
    }

    public void executeNext() {
        mips.executeNext();
    }

    public void executeAll() {
        mips.executeAll();
    }

    public int getReg(int regNum) {
        return mips.getReg().getRegister(regNum);
    }

    public void setReg(int regNum, int val) {
        mips.getReg().setRegister(regNum, val);
    }

    public void regToName(int regNum) {
        Registers.registerToName(regNum);
    }

    public void handleOpen() throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open project JSON");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File selectedFile = fc.showOpenDialog(this.stage);
        ProgramLoader pl = new ProgramLoader(selectedFile);
        this.mips = pl.getMips();
    }

}

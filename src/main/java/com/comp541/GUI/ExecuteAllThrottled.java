package com.comp541.GUI;

import com.comp541.mips.Mips;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.SwInstruction;
import com.comp541.mips.instructions.Instruction;
import com.comp541.mips.memory.MappedMemoryUnit;
import com.comp541.mips.memory.ScreenMemory;
import com.comp541.mips.memory.Sound;

import java.util.Objects;

public class ExecuteAllThrottled implements Runnable {
    private static final double TARGET_CONSTANT = 0.85;

    private final Mips mips;
    private final double clockSpeed;
    private final Registers reg;
    private final int smemStartAddr;
    private final int smemEndAddr;
    private final int soundAddr;
    private static Thread sound;

    public ExecuteAllThrottled(Mips mips, double clockSpeed) {
        this.mips = mips;
        this.clockSpeed = clockSpeed;
        reg = mips.getReg();
        MappedMemoryUnit screenMemory =
                Objects.requireNonNull(mips.getMemory().getMemUnits().stream()
                        .filter(mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof ScreenMemory)
                        .findFirst()
                        .orElse(null));
        smemStartAddr = screenMemory.getStartAddr();
        smemEndAddr = screenMemory.getEndAddr();
        MappedMemoryUnit soundMemory =
                Objects.requireNonNull(mips.getMemory().getMemUnits().stream()
                    .filter(mappedMemoryUnit -> mappedMemoryUnit.getMemUnit() instanceof Sound)
                    .findFirst()
                    .orElse(null));
        soundAddr = soundMemory.getStartAddr();
    }

    @Override
    public void run() {
        double targetInstrPerMillisec = clockSpeed * 1000000 / 1000 * TARGET_CONSTANT;
        long totalInstructionsExecuted = 0;
        long instructionsExecuted = 0;
        Stopwatch executionStopwatch = new Stopwatch();
        Stopwatch throttleStopwatch = new Stopwatch();

        SoundController sc = new SoundController(mips);
        sound = new Thread(sc);
        sound.start();

        while (MainController.getIsExecuting()) {
            long timeElapsed = throttleStopwatch.getTimeElapsed();
            if ((throttleStopwatch.getTimeElapsed() != 0)
                    && (instructionsExecuted <= targetInstrPerMillisec * timeElapsed)) {
                Instruction nextInstruction = mips.executeNextAndReturnInstruction();
                totalInstructionsExecuted++;
                // Rendering logic
                if (nextInstruction instanceof SwInstruction) {
                    SwInstruction swInstruction = (SwInstruction) nextInstruction;
                    int targetAddr =
                            reg.getRegister(swInstruction.getS()) + signExtend(swInstruction.getImmediate());
                    // Display rendering condition
                    if (smemStartAddr <= targetAddr && targetAddr <= smemEndAddr) {
                        VgaDisplayBMPController.renderVGA((targetAddr - smemStartAddr) >> 2);
                    } else if (targetAddr == soundAddr) {
                        sc.changeNote();
                        sound.interrupt();
                    } else if (targetAddr == Integer.parseInt("1003000c", 16)) {
                        LedController.renderLED();
                    }
                }
                instructionsExecuted++;
                totalInstructionsExecuted++;
            } else if (throttleStopwatch.getTimeElapsed() >= 1) {
                throttleStopwatch.reset();
                instructionsExecuted = 0;
            }
        }

        if (sound != null) {
            try {
                sound.interrupt();
                sound.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long delta = executionStopwatch.getTimeElapsed() / 1000;
        System.out.println("Time: " + delta + "s");
        System.out.println("Mips instructions executed: " + totalInstructionsExecuted);
        System.out.println(
                "Throttled clock speed (avg): " + totalInstructionsExecuted / 1000000.0 / delta + " MHz");
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

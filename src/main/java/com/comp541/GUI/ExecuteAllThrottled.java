package com.comp541.GUI;

import com.comp541.mips.Mips;
import com.comp541.mips.Registers;
import com.comp541.mips.instructions.IType.SwInstruction;
import com.comp541.mips.instructions.Instruction;
import com.comp541.mips.memory.MappedMemoryUnit;
import com.comp541.mips.memory.ScreenMemory;
import com.comp541.mips.memory.Sound;

import java.util.Objects;

import static com.comp541.Main.LOGGER;
import static com.comp541.mips.instructions.IType.ITypeInstruction.signExtend;

public class ExecuteAllThrottled implements Runnable {
    private final double CLOCK_SPEED_MHZ = 12.5;
    private final long nanosPerInstruction = 1_000_000_000L / (long) (CLOCK_SPEED_MHZ * 1_000_000);

    private final Mips mips;
    private final Registers reg;
    private final int smemStartAddr;
    private final int smemEndAddr;
    private final int soundAddr;
    private static Thread sound;

    public ExecuteAllThrottled(Mips mips) {
        this.mips = mips;
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
        long targetInstructionsPerSec = (long) (CLOCK_SPEED_MHZ * 1_000_000);
        long nanosPerInstruction = 1_000_000_000L / targetInstructionsPerSec;
        long instructionsExecuted = 0;

        SoundController sc = new SoundController(mips);
        sound = new Thread(sc);
        sound.start();

        long startTime = System.nanoTime();

        while (MainController.getIsExecuting()) {
            Instruction nextInstruction = mips.executeNextAndReturnInstruction();
            instructionsExecuted++;

            // Rendering logic
            if (nextInstruction instanceof SwInstruction swInstruction) {
                int targetAddr =
                        reg.getRegister(swInstruction.getS()) + signExtend(swInstruction.getImmediate());
                // Display rendering condition
                if (smemStartAddr <= targetAddr && targetAddr <= smemEndAddr) {
                    VgaDisplayBMPController.renderVGA((targetAddr - smemStartAddr) >> 2);
                } else if (targetAddr == soundAddr) {
                    sc.changeNote();
                    sound.interrupt();
                // TODO: Make LED address configurable
                } else if (targetAddr == Integer.parseInt("1003000c", 16)) {
                    LedController.renderLED();
                }
            }

            // Throttle execution to maintain CLOCK_SPEED_MHZ
            long elapsedTime = System.nanoTime() - startTime;
            long sleepTime = nanosPerInstruction - elapsedTime;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1_000_000, (int) (sleepTime % 1_000_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
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

        double delta = (double) (System.nanoTime() / startTime) / 1_000_000_000L;
        LOGGER.info("Time: {}s", delta);
        LOGGER.info("Mips instructions executed: {}", instructionsExecuted);
        LOGGER.info("Throttled clock speed (avg): {} MHz", (double) instructionsExecuted / 1_000_000_000L / delta);
    }
}

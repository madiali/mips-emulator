package com.comp541.GUI;

import com.comp541.mips.Mips;
import com.comp541.mips.memory.MemoryUnit;

import javax.sound.midi.*;

import java.util.Objects;

public class SoundController implements Runnable {

    // This value can be [0-127], 70 is a comfortable value
    private final int STD_VELO = 70;
    private final int WAIT_TIME = 5000;
    private final int PITCH_NEUTRAL = 8192;
    private final double[] frequencies = {27.5, 29.135, 30.868, 32.703, 34.648, 36.708, 38.898, 41.203, 43.654, 46.249, 48.999, 51.913, 55, 58.27, 61.736, 65.406, 69.296, 73.416, 77.796, 82.406, 87.308, 92.498, 97.998, 103.826, 110, 116.54, 123.472, 130.812, 138.592, 146.832, 155.592, 164.812, 174.616, 184.996, 195.996, 207.652, 220, 233.08, 246.944, 261.624, 277.184, 293.664, 311.184, 329.624, 349.232, 369.992, 391.992, 415.304, 440, 466.16, 493.888, 523.248, 554.368, 587.328, 622.368, 659.248, 698.464, 739.984, 783.984, 830.608, 880, 932.32, 987.776, 1046.496, 1108.736, 1174.656, 1244.736, 1318.496, 1396.928, 1479.968, 1567.968, 1661.216, 1760, 1864.64, 1975.552, 2092.992, 2217.472, 2349.312, 2489.472, 2636.992, 2793.856, 2959.936, 3135.936, 3322.432, 3520, 3729.28, 3951.104, 4185.984, 4434.944, 4698.624, 4978.944, 5273.984, 5587.712, 5919.872, 6271.872, 6644.864, 7040, 7458.56, 7902.208, 8371.968, 8869.888, 9397.248, 9957.888, 10547.968, 11175.424, 11839.744, 12543.744};

    private volatile int midiNote;
    private volatile int pitchBend;
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private static MemoryUnit soundMemory;

    public SoundController(Mips mips) {
        soundMemory =
                    Objects.requireNonNull(mips.getMemory().getMemUnits().stream()
                                    .filter(mappedMemoryUnit -> mappedMemoryUnit.getName().equals("Sound"))
                                    .findFirst()
                                    .orElse(null))
                            .getMemUnit();
        initializeSound();
        changeNote();
    }

    private void initializeSound() {
        try {
            // Get default synthesizer
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            // Get the default MIDI channel (channel 0)
            MidiChannel[] midiChannels = synthesizer.getChannels();
            channel = midiChannels[0];

            // Change instrument to 'Lead 1 (Square)'
            channel.programChange(80);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the current MIDI note based on the period retrieved from memory.
     *
     * This method calculates the frequency in Hz from a period value stored in
     * `soundMemory`, converts it to a MIDI note using the `snap()` method, and
     * adjusts the `midiNote` and `pitchBend` fields accordingly.
     *
     * Modifies:
     * - `midiNote`: Set based on the frequency derived from the period. If the period is 0,
     *   it is set to 0 (no valid note).
     * - `pitchBend`: Reset to `PITCH_NEUTRAL` if the period is 0, or adjusted during the note
     *   calculation in the `snap()` method.
     */
    public void changeNote() {
        long period = Integer.toUnsignedLong(soundMemory.getMemoryUnit(0));
        if (period == 0) {
            midiNote = 0;
            pitchBend = PITCH_NEUTRAL;
            return;
        }
        int hz = (int) Math.round(1 / (period / Math.pow(10, 8)));
        setMidiAndPitch(hz);
    }


    @Override
    public void run() {
        if (synthesizer == null || channel == null) {
            initializeSound();
        }
        while (MainController.getIsExecuting()) {
            if (midiNote > 0) {
                int cur = midiNote;
                channel.setPitchBend(pitchBend);
                channel.noteOn(cur, STD_VELO);
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException ignored) {}

                channel.noteOff(cur);
            } else {
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException ignored) {}
            }
        }

        if (synthesizer.isOpen()) {
            synthesizer.close();
        }
    }


    /**
     * Snaps the given frequency to the closest MIDI note.
     *
     * @param freq The frequency in Hz to be converted to a MIDI note.
     *             If the frequency is below 25 Hz, it is considered out of range,
     *             and the method sets `midiNote` to 0 (indicating no note).
     *             If the frequency is between 25 and 28 Hz, it snaps to the lowest
     *             possible MIDI note (21).
     *             For frequencies greater than or equal to 28 Hz, it finds the closest
     *             MIDI note based on the `frequencies[]` array and sets the `midiNote`
     *             accordingly. Then the pitch bend is calculated as a percentage of bend
     *             from the closest true MIDI note.
     *
     * Modifies:
     * - `midiNote` to the corresponding MIDI note based on the input frequency.
     * - `pitchBend` is set to an appropriate bend to match the frequency.
     *
     * Edge cases:
     * - Frequencies lower than 25 Hz will set `midiNote` to 0 (no valid note).
     * - The algorithm assumes the `frequencies[]` array is pre-sorted in ascending
     *   order and that it corresponds to the note frequencies for MIDI note 21 and above.
     */
    private void setMidiAndPitch(int freq) {
        pitchBend = PITCH_NEUTRAL;
        if (freq < 25) {
            midiNote = 0;
            return;
        } else if (freq < 28) {
            midiNote = 21;
            return;
        }

        for (int i = 1; i < frequencies.length - 1; i++) {
            if (freq < (frequencies[i] + frequencies[i+1])/(double)2) {
                midiNote = i + 21;
                if (freq < frequencies[i]) {
                    double percent = (frequencies[i] - freq) / (frequencies[i] - frequencies[i-1]);
                    pitchBend = (int) Math.round(PITCH_NEUTRAL - (4095.5 * percent));
                } else if (freq > frequencies[i]) {
                    double percent = (freq - frequencies[i]) / (frequencies[i+1] - frequencies[i]);
                    pitchBend = (int) Math.round(PITCH_NEUTRAL + (4095.5 * percent));
                }
                return;
            }
        }
        midiNote = 0;
    }
}


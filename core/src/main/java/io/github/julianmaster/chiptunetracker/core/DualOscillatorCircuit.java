package io.github.julianmaster.chiptunetracker.core;

import io.github.julianmaster.chiptunetracker.model.Note;
import io.github.julianmaster.chiptunetracker.model.Notes;
import io.github.julianmaster.chiptunetracker.model.Sound;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;
import com.softsynth.shared.time.TimeStamp;

public class DualOscillatorCircuit extends CustomCircuit {

    /**
     *
     *  freqRamp1 -- freqAdder1 - osc1 \
     *       sin -+                    +- oscAdder -- multiply - envelope
     *  freqRamp2 -- freqAdder2 - osc2 /   ampRamp /
     *
     *
     */

    private CustomLinearRamp freqRamp1;
    private CustomLinearRamp freqRamp2;
    private SineOscillator sin;
    private Add freqAdder1;
    private Add freqAdder2;
    private UnitOscillator osc1;
    private UnitOscillator osc2;
    private Add oscAdder;
    private CustomLinearRamp ampRamp;
    private Multiply multiply;
    private EnvelopeDAHDSR envelope;

    public DualOscillatorCircuit(UnitOscillator osc1, UnitOscillator osc2) {
        this.osc1 = osc1;
        this.osc2 = osc2;

        add(osc1);
        add(osc2);
        add(freqRamp1 = new CustomLinearRamp());
        add(freqRamp2 = new CustomLinearRamp());
        add(sin = new SineOscillator());
        add(freqAdder1 = new Add());
        add(freqAdder2 = new Add());
        add(oscAdder = new Add());
        add(envelope = new EnvelopeDAHDSR());
        add(ampRamp = new CustomLinearRamp());
        add(multiply = new Multiply());

        freqRamp1.output.connect(freqAdder1.inputA);
        freqRamp2.output.connect(freqAdder2.inputA);
        sin.output.connect(freqAdder1.inputB);
        sin.output.connect(freqAdder2.inputB);
        freqAdder1.output.connect(osc1.frequency);
        freqAdder2.output.connect(osc2.frequency);
        osc1.output.connect(oscAdder.inputA);
        osc2.output.connect(oscAdder.inputB);
        oscAdder.output.connect(multiply.inputA);
        ampRamp.output.connect(multiply.inputB);
        multiply.output.connect(envelope.amplitude);

        envelope.setupAutoDisable(this);
        envelope.attack.set(0.01d);
        envelope.sustain.set(1.0d);
        envelope.release.set(0.01d);
    }

    @Override
    public UnitOutputPort getOutput() {
        return envelope.output;
    }

    @Override
    public void usePreset(Sound lastSound, int presetIndex, double frequency, double amplitude, double duration, TimeStamp timeStamp) {
        switch (presetIndex) {
            case 0:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(frequency, timeStamp);
                freqRamp1.input.set(frequency, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set(frequency*127d / 128d, timeStamp);
                freqRamp2.input.set(frequency*127d / 128d, timeStamp);
                sin.frequency.set(0.0f, timeStamp);
                sin.amplitude.set(0.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude, timeStamp);
                ampRamp.input.set(amplitude, timeStamp);
                break;

            case 1:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(lastSound != null ? Notes.getFrequency(lastSound.octave, lastSound.note) : Notes.getFrequency(0, Note.C), timeStamp);
                freqRamp1.input.set(frequency, timeStamp);
                freqRamp1.time.set(duration, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set((lastSound != null ? Notes.getFrequency(lastSound.octave, lastSound.note) : Notes.getFrequency(0, Note.C))*127d / 128d, timeStamp);
                freqRamp2.input.set(frequency*127d / 128d, timeStamp);
                freqRamp2.time.set(duration, timeStamp);
                sin.frequency.set(0.0f, timeStamp);
                sin.amplitude.set(0.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude, timeStamp);
                ampRamp.input.set(amplitude, timeStamp);
                break;

            case 2:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(frequency, timeStamp);
                freqRamp1.input.set(frequency, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set(frequency*127d / 128d, timeStamp);
                freqRamp2.input.set(frequency*127d / 128d, timeStamp);
                sin.frequency.set(10.0f, timeStamp);
                sin.amplitude.set(4.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude, timeStamp);
                ampRamp.input.set(amplitude, timeStamp);
                break;

            case 3:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(frequency, timeStamp);
                freqRamp1.input.set(Notes.getFrequency(0, Note.C), timeStamp);
                freqRamp1.time.set(duration, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set(frequency*127d / 128d, timeStamp);
                freqRamp2.input.set(Notes.getFrequency(0, Note.C)*127d / 128d, timeStamp);
                freqRamp2.time.set(duration, timeStamp);
                sin.frequency.set(0.0f, timeStamp);
                sin.amplitude.set(0.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude, timeStamp);
                ampRamp.input.set(amplitude, timeStamp);
                break;

            case 4:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(frequency, timeStamp);
                freqRamp1.input.set(frequency, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set(frequency*127d / 128d, timeStamp);
                freqRamp2.input.set(frequency*127d / 128d, timeStamp);
                sin.frequency.set(0.0f, timeStamp);
                sin.amplitude.set(0.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude*0.1d, timeStamp);
                ampRamp.input.set(amplitude, timeStamp);
                ampRamp.time.set(duration, timeStamp);
                break;

            case 5:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(frequency, timeStamp);
                freqRamp1.input.set(frequency, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set(frequency*127d / 128d, timeStamp);
                freqRamp2.input.set(frequency*127d / 128d, timeStamp);
                sin.frequency.set(0.0f, timeStamp);
                sin.amplitude.set(0.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude, timeStamp);
                ampRamp.input.set(amplitude*0.1d, timeStamp);
                ampRamp.time.set(duration, timeStamp);
                break;

            case 6:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(frequency, timeStamp);
                freqRamp1.input.set(frequency, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set(frequency*127d / 128d, timeStamp);
                freqRamp2.input.set(frequency*127d / 128d, timeStamp);
                sin.frequency.set(0.0f, timeStamp);
                sin.amplitude.set(0.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude, timeStamp);
                ampRamp.input.set(amplitude, timeStamp);
                break;

            case 7:
                freqRamp1.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp1.startValue.set(frequency, timeStamp);
                freqRamp1.input.set(frequency, timeStamp);
                freqRamp2.isStart.set(UnitGenerator.TRUE, timeStamp);
                freqRamp2.startValue.set(frequency*127d / 128d, timeStamp);
                freqRamp2.input.set(frequency*127d / 128d, timeStamp);
                sin.frequency.set(0.0f, timeStamp);
                sin.amplitude.set(0.0f, timeStamp);
                ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
                ampRamp.startValue.set(amplitude, timeStamp);
                ampRamp.input.set(amplitude, timeStamp);
                break;
        }
    }

    @Override
    public void noteOn(double frequency, double amplitude, TimeStamp timeStamp) {
//        freqAdder1.inputA.set(frequency, timeStamp);
//        freqAdder2.inputA.set(frequency*127d / 128d, timeStamp);

        envelope.input.on(timeStamp);
    }

    @Override
    public void noteOff(TimeStamp timeStamp) {
        envelope.input.off(timeStamp);
    }
}

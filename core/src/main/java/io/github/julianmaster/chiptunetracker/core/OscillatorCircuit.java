package io.github.julianmaster.chiptunetracker.core;

import io.github.julianmaster.chiptunetracker.model.Note;
import io.github.julianmaster.chiptunetracker.model.Notes;
import io.github.julianmaster.chiptunetracker.model.Sound;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;
import com.softsynth.shared.time.TimeStamp;

public class OscillatorCircuit extends CustomCircuit {

	/**
	 *
	 *
	 *  freqRamp -- freqAdder - osc -- multiply - envelope
	 *       sin /          ampRamp /
	 *
	 */


	private CustomLinearRamp freqRamp;
	private SineOscillator sin;
	private UnitOscillator osc;
	private Add freqAdder;
	private CustomLinearRamp ampRamp;
	private Multiply multiply;
	private EnvelopeDAHDSR envelope;

	public OscillatorCircuit(UnitOscillator osc) {
		this.osc = osc;

		add(osc);
		add(freqRamp = new CustomLinearRamp());
		add(sin = new SineOscillator());
		add(freqAdder = new Add());
		add(envelope = new EnvelopeDAHDSR());
		add(ampRamp = new CustomLinearRamp());
		add(multiply = new Multiply());

		freqRamp.output.connect(freqAdder.inputA);
		sin.output.connect(freqAdder.inputB);
		freqAdder.output.connect(osc.frequency);
		osc.output.connect(multiply.inputA);
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
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(frequency, timeStamp);
				freqRamp.input.set(frequency, timeStamp);
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ampRamp.startValue.set(amplitude, timeStamp);
				ampRamp.input.set(amplitude, timeStamp);
				break;

			case 1:
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(lastSound != null ? Notes.getFrequency(lastSound.octave, lastSound.note) : Notes.getFrequency(0, Note.C), timeStamp);
				freqRamp.input.set(frequency, timeStamp);
				freqRamp.time.set(duration, timeStamp);
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ampRamp.startValue.set(amplitude, timeStamp);
				ampRamp.input.set(amplitude, timeStamp);
				break;

			case 2:
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(frequency, timeStamp);
				freqRamp.input.set(frequency, timeStamp);
				sin.frequency.set(10.0f, timeStamp);
				sin.amplitude.set(4.0f, timeStamp);
				ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ampRamp.startValue.set(amplitude, timeStamp);
				ampRamp.input.set(amplitude, timeStamp);
				break;

			case 3:
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(frequency, timeStamp);
				freqRamp.input.set(Notes.getFrequency(0, Note.C), timeStamp);
				freqRamp.time.set(duration, timeStamp);
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ampRamp.startValue.set(amplitude, timeStamp);
				ampRamp.input.set(amplitude, timeStamp);
				break;

			case 4:
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(frequency, timeStamp);
				freqRamp.input.set(frequency, timeStamp);
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ampRamp.startValue.set(amplitude*0.1d, timeStamp);
				ampRamp.input.set(amplitude, timeStamp);
				ampRamp.time.set(duration, timeStamp);
				break;

			case 5:
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(frequency, timeStamp);
				freqRamp.input.set(frequency, timeStamp);
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ampRamp.startValue.set(amplitude, timeStamp);
				ampRamp.input.set(amplitude*0.1d, timeStamp);
				ampRamp.time.set(duration, timeStamp);
				break;

			case 6:
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(frequency, timeStamp);
				freqRamp.input.set(frequency, timeStamp);
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ampRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ampRamp.startValue.set(amplitude, timeStamp);
				ampRamp.input.set(amplitude, timeStamp);
				break;

			case 7:
				freqRamp.isStart.set(UnitGenerator.TRUE, timeStamp);
				freqRamp.startValue.set(frequency, timeStamp);
				freqRamp.input.set(frequency, timeStamp);
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
//		freqAdder.inputA.set(frequency, timeStamp);
		envelope.input.on(timeStamp);
	}

	@Override
	public void noteOff(TimeStamp timeStamp) {
		envelope.input.off(timeStamp);
	}
}

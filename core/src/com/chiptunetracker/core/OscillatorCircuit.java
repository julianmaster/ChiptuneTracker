package com.chiptunetracker.core;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;
import com.softsynth.shared.time.TimeStamp;

public class OscillatorCircuit extends CustomCircuit {
	private SineOscillator sin;
	private Add freqAdder;
	private UnitOscillator osc;
	private EnvelopeDAHDSR ampEnv;
	private CustomLinearRamp ramp;
	private Multiply multiply;

	public OscillatorCircuit(UnitOscillator osc) {
		this.osc = osc;
		
		add(osc);
		add(sin = new SineOscillator());
		add(freqAdder = new Add());
		add(ampEnv = new EnvelopeDAHDSR());
		add(ramp = new CustomLinearRamp());
		add(multiply = new Multiply());


		sin.output.connect(freqAdder.inputB);
		freqAdder.output.connect(osc.frequency);
		osc.output.connect(multiply.inputA);
		ramp.output.connect(multiply.inputB);
		multiply.output.connect(ampEnv.amplitude);

		ampEnv.setupAutoDisable(this);
		ampEnv.attack.set(0.01d);
		ampEnv.sustain.set(1.0d);
		ampEnv.release.set(0.01d);
	}

	@Override
	public UnitOutputPort getOutput() {
		return ampEnv.output;
	}

	@Override
	public void usePreset(int presetIndex, double frequency, double amplitude, double duration, TimeStamp timeStamp) {
		switch (presetIndex) {
			case 0:
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				break;

			case 1:
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				break;

			case 2:
				sin.frequency.set(10.0f, timeStamp);
				sin.amplitude.set(4.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				break;

			case 3:
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				break;

			case 4:
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude*0.1d, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				ramp.time.set(duration, timeStamp);
				break;

			case 5:
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude*0.1d, timeStamp);
				ramp.time.set(duration, timeStamp);
				break;

			case 6:
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				break;

			case 7:
				sin.frequency.set(0.0f, timeStamp);
				sin.amplitude.set(0.0f, timeStamp);
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				break;
		}
	}

	@Override
	public void noteOn(double frequency, double amplitude, TimeStamp timeStamp) {
		freqAdder.inputA.set(frequency, timeStamp);
		ampEnv.input.on(timeStamp);
	}
	
	@Override
	public void noteOff(TimeStamp timeStamp) {
		ampEnv.input.off(timeStamp);
	}
}

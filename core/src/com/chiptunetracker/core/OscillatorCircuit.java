package com.chiptunetracker.core;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;
import com.softsynth.shared.time.TimeStamp;

public class OscillatorCircuit extends CustomCircuit {
	private UnitOscillator osc;
	private EnvelopeDAHDSR ampEnv;
	private ContinuousRamp ramp;

	public OscillatorCircuit(UnitOscillator osc) {
		this.osc = osc;
		
		add(osc);
		add(ampEnv = new EnvelopeDAHDSR());

		osc.output.connect(ampEnv.amplitude);

		ampEnv.setupAutoDisable(this);
		ampEnv.attack.set(0.01d);
		ampEnv.sustain.set(1.0d);
		ampEnv.release.set(0.01d);

		usePreset(0);
	}

	@Override
	public UnitOutputPort getOutput() {
		return ampEnv.output;
	}

	@Override
	public void usePreset(int presetIndex, double duration, TimeStamp timeStamp) {
		switch (presetIndex) {
			case 0:

				break;

			default:
				break;
		}
	}

	@Override
	public void noteOn(double frequency, double amplitude, TimeStamp timeStamp) {
		osc.frequency.set(frequency, timeStamp);
		osc.amplitude.set(amplitude, timeStamp);
		
		ampEnv.input.on(timeStamp);
	}
	
	@Override
	public void noteOff(TimeStamp timeStamp) {
		ampEnv.input.off(timeStamp);
	}
}

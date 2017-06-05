package com.chiptunetracker.core;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.UnitOscillator;
import com.softsynth.shared.time.TimeStamp;

public class OscillatorCircuit extends CustomCircuit {
	private UnitOscillator osc;
	private EnvelopeDAHDSR ampEnv;

	public OscillatorCircuit(UnitOscillator osc) {
		this.osc = osc;
		
		add(osc);
		add(ampEnv = new EnvelopeDAHDSR());

		osc.output.connect(ampEnv.amplitude);

		ampEnv.setupAutoDisable(this);

		usePreset(0);
	}

	@Override
	public UnitOutputPort getOutput() {
		return ampEnv.output;
	}
	
	@Override
	public void usePreset(int presetIndex) {
		switch (presetIndex) {
			case 0:
				ampEnv.attack.set(0.01d);
				ampEnv.sustain.set(1.0d);
				ampEnv.release.set(0.01d);
				break;

			case 1:
				ampEnv.attack.set(0.1d);
				ampEnv.decay.set(0.2d);
				ampEnv.sustain.set(0.0d);
				ampEnv.release.set(0.7d);
				break;

			case 2:
				ampEnv.attack.set(0.9d);
				ampEnv.decay.set(0.05d);
				ampEnv.sustain.set(0.0d);
				ampEnv.release.set(0.05d);
				break;
	
			default:
				ampEnv.attack.set(0.01d);
				ampEnv.sustain.set(1.0d);
				ampEnv.release.set(0.01d);
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

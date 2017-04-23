package com.chiptunetracker.core;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.WhiteNoise;
import com.softsynth.shared.time.TimeStamp;

public class WhiteNoiseCircuit extends CustomCircuit {
	private WhiteNoise gen;
	private EnvelopeDAHDSR ampEnv;

	public WhiteNoiseCircuit(WhiteNoise gen) {
		this.gen = gen;
		
		add(gen);
		add(ampEnv = new EnvelopeDAHDSR());
		
		gen.output.connect(ampEnv.amplitude);
		
//		gen.amplitude.set(0);
		ampEnv.setupAutoDisable(this);
		
		usePreset(0);
	}

	@Override
	public UnitOutputPort getOutput() {
		return ampEnv.getOutput();
	}
	
	@Override
	public void usePreset(int presetIndex) {
		switch (presetIndex) {
			case 0:
				ampEnv.attack.set(0.01);
				ampEnv.sustain.set(1.0);
				ampEnv.release.set(0.01);
				break;

			default:
				ampEnv.attack.set(0.01);
				ampEnv.sustain.set(1.0);
				ampEnv.release.set(0.01);
				break;
		}
	}
	
	@Override
	public void noteOn(double frequency, double amplitude, TimeStamp timeStamp) {
		gen.amplitude.set(amplitude, timeStamp);
		gen.start(timeStamp);
		
		ampEnv.input.on(timeStamp);
	}

	@Override
	public void noteOff(TimeStamp timeStamp) {
		ampEnv.input.off(timeStamp);
		gen.stop(timeStamp);
		gen.amplitude.set(0);
	}
}

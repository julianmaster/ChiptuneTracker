package com.chiptunetracker.core;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.EnvelopeAttackDecay;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.UnitOscillator;
import com.softsynth.shared.time.TimeStamp;

public class OscillatorCircuit extends CustomCircuit {
	private UnitOscillator osc;
//	private EnvelopeDAHDSR ampEnv;
	private EnvelopeAttackDecay ampEnv;

	public OscillatorCircuit(UnitOscillator osc) {
		this.osc = osc;
		
		add(osc);
//		add(ampEnv = new EnvelopeDAHDSR());
		add(ampEnv = new EnvelopeAttackDecay());

		osc.output.connect(ampEnv.amplitude);

		ampEnv.setupAutoDisable(this);

		usePreset(0);
	}

	@Override
	public UnitOutputPort getOutput() {
		return ampEnv.output;
	}

	@Override
	public void usePreset(int presetIndex, double duration, TimeStamp timeStamp) {
//		switch (presetIndex) {
//			case 0:
//				ampEnv.attack.set(0.01d, timeStamp);
//				ampEnv.hold.set(0.0d, timeStamp);
//				ampEnv.decay.set(0.2d, timeStamp);
//				ampEnv.sustain.set(1.0d, timeStamp);
//				ampEnv.release.set(0.01d, timeStamp);
//				break;
//
//			case 4:
//				ampEnv.attack.set(duration - 2 * 0.01d, timeStamp);
//				ampEnv.hold.set(0.0d, timeStamp);
//				ampEnv.decay.set(0.01d, timeStamp);
//				ampEnv.sustain.set(1.0d, timeStamp);
//				ampEnv.release.set(0.01d, timeStamp);
//				break;
//
//			case 5:
//				ampEnv.attack.set(0.01d, timeStamp);
//				ampEnv.hold.set(0.0d, timeStamp);
//				ampEnv.decay.set(duration*1/3 - 0.01d, timeStamp);
//				ampEnv.sustain.set(0.1d, timeStamp);
//				ampEnv.release.set(duration*2/3 - 0.01d, timeStamp);
//				break;
//
//			default:
//				ampEnv.attack.set(0.01d, timeStamp);
//				ampEnv.hold.set(0.0d, timeStamp);
//				ampEnv.decay.set(0.2d, timeStamp);
//				ampEnv.sustain.set(1.0d, timeStamp);
//				ampEnv.release.set(0.01d, timeStamp);
//				break;
//		}

		switch (presetIndex) {
			case 0:
				ampEnv.attack.set(0.05d, timeStamp);
				ampEnv.decay.set(duration - 0.05d, timeStamp);
				break;

			case 4:
				ampEnv.attack.set(duration - 0.05d, timeStamp);
				ampEnv.decay.set(0.05d, timeStamp);
				break;

			case 5:
				ampEnv.attack.set(0.05d, timeStamp);
				ampEnv.decay.set(duration - 0.05d, timeStamp);
				break;

			default:
				ampEnv.attack.set(0.05d, timeStamp);
				ampEnv.decay.set(duration - 0.05d, timeStamp);
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

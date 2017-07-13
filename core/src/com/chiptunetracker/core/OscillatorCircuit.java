package com.chiptunetracker.core;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;
import com.softsynth.shared.time.TimeStamp;

public class OscillatorCircuit extends CustomCircuit {
	private UnitOscillator osc;
	private EnvelopeDAHDSR ampEnv;
	private CustomRamp ramp;

	public OscillatorCircuit(UnitOscillator osc) {
		this.osc = osc;
		
		add(osc);
		add(ampEnv = new EnvelopeDAHDSR());
		add(ramp = new CustomRamp());

		ramp.output.connect(osc.amplitude);
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
	public void usePreset(int presetIndex, double frequency, double amplitude, double duration, TimeStamp timeStamp) {
		switch (presetIndex) {
			case 0:
				ramp.isStart = true;
				ramp.startValue.set(amplitude, timeStamp);
				ramp.endValue.set(amplitude, timeStamp);
				break;

			case 1:
				ramp.isStart = true;
				ramp.startValue.set(amplitude, timeStamp);
				ramp.endValue.set(amplitude, timeStamp);
				break;

			case 2:
				ramp.isStart = true;
				ramp.startValue.set(amplitude, timeStamp);
				ramp.endValue.set(amplitude, timeStamp);
				break;

			case 3:
				ramp.isStart = true;
				ramp.startValue.set(amplitude, timeStamp);
				ramp.endValue.set(amplitude, timeStamp);
				break;

			case 4:
				ramp.isStart = true;
				ramp.startValue.set(0.0d, timeStamp);
				ramp.endValue.set(amplitude, timeStamp);
				ramp.duration.set(duration, timeStamp);
				ramp.time.set(duration, timeStamp);
				break;

			case 5:
				ramp.isStart = true;
				ramp.startValue.set(amplitude, timeStamp);
				ramp.endValue.set(0.0d, timeStamp);
				ramp.duration.set(duration, timeStamp);
				ramp.time.set(duration, timeStamp);
				break;

			case 6:
				ramp.isStart = true;
				ramp.startValue.set(amplitude, timeStamp);
				ramp.endValue.set(amplitude, timeStamp);
				break;

			case 7:
				ramp.isStart = true;
				ramp.startValue.set(amplitude, timeStamp);
				ramp.endValue.set(amplitude, timeStamp);
				break;
		}
	}

	@Override
	public void noteOn(double frequency, double amplitude, TimeStamp timeStamp) {
		osc.frequency.set(frequency, timeStamp);
//		ramp.startValue.set(amplitude, timeStamp);
//		ramp.endValue.set(amplitude, timeStamp);

		ampEnv.input.on(timeStamp);
	}
	
	@Override
	public void noteOff(TimeStamp timeStamp) {
		ampEnv.input.off(timeStamp);
	}
}

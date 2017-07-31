package com.chiptunetracker.core;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;
import com.softsynth.shared.time.TimeStamp;

public class OscillatorCircuit extends CustomCircuit {
	private UnitOscillator osc;
	private EnvelopeDAHDSR ampEnv;
	private CustomLinearRamp ramp;
	private Multiply multiply;

	public OscillatorCircuit(UnitOscillator osc) {
		this.osc = osc;
		
		add(osc);
		add(ampEnv = new EnvelopeDAHDSR());
		add(ramp = new CustomLinearRamp());
		add(multiply = new Multiply());

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
	public boolean usePreset(int presetIndex, double frequency, double amplitude, double duration, TimeStamp timeStamp) {
		switch (presetIndex) {
			case 0:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				return false;

			case 1:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				return false;

			case 2:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				return false;

			case 3:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				return false;

			case 4:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(0.0d, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				ramp.time.set(duration, timeStamp);
				return true;

			case 5:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude*0.1d, timeStamp);
				ramp.time.set(duration, timeStamp);
				return true;

			case 6:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				return false;

			case 7:
				ramp.isStart.set(UnitGenerator.TRUE, timeStamp);
				ramp.startValue.set(amplitude, timeStamp);
				ramp.input.set(amplitude, timeStamp);
				return false;
		}

		return false;
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

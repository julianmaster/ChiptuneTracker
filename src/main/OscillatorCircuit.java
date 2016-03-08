package main;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.EnvelopeDAHDSR;
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
		
//		ampEnv.attack.set(0.1);
//		ampEnv.hold.set(0.0);
//		ampEnv.decay.set(8.0);
//		ampEnv.sustain.set(0.0);
//		ampEnv.release.set(0.01);
		
		
//		ampEnv.attack.set(0.01);
//		ampEnv.sustain.set(0.0);
//		ampEnv.release.set(1.0);
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

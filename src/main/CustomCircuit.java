package main;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitVoice;
import com.softsynth.shared.time.TimeStamp;

public class CustomCircuit extends Circuit implements UnitVoice {
	private UnitOscillator osc;
	private EnvelopeDAHDSR ampEnv;
	
	public CustomCircuit(UnitOscillator osc) {
		this.osc = osc;
		
		add(osc);
		add(ampEnv = new EnvelopeDAHDSR());
		
		osc.output.connect(ampEnv.amplitude);
		
		ampEnv.setupAutoDisable(this);
		
//		ampEnv.attack.set(0.1);
//		ampEnv.hold.set(0.0);
//		ampEnv.decay.set(8.0);
//		ampEnv.sustain.set(0.0);
//		ampEnv.release.set(0.01);
		
		
		ampEnv.attack.set(0.01);
		ampEnv.sustain.set(1.0);
		ampEnv.release.set(0.1);
		
//		ampEnv.attack.set(0.01);
//		ampEnv.sustain.set(0.0);
//		ampEnv.release.set(1.0);
	}

	@Override
	public UnitOutputPort getOutput() {
		return ampEnv.output;
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

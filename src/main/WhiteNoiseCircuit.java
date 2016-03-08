package main;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.WhiteNoise;
import com.softsynth.shared.time.TimeStamp;

public class WhiteNoiseCircuit extends CustomCircuit {
	private WhiteNoise gen;

	public WhiteNoiseCircuit(WhiteNoise gen) {
		this.gen = gen;
		
		add(gen);
		
		gen.amplitude.set(0);
	}

	@Override
	public UnitOutputPort getOutput() {
		return gen.getOutput();
	}
	
	@Override
	public void noteOn(double frequency, double amplitude, TimeStamp timeStamp) {
		gen.amplitude.set(amplitude);
		
		gen.start(timeStamp);
	}

	@Override
	public void noteOff(TimeStamp timeStamp) {
		gen.stop(timeStamp);
		gen.amplitude.set(0);
	}
}

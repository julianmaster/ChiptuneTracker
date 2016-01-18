package main;

import com.softsynth.jsyn.LineOut;
import com.softsynth.jsyn.SawtoothOscillatorBL;
import com.softsynth.jsyn.Synth;

public class ChiptuneTracker {
	
	SawtoothOscillatorBL osc;
	LineOut lineOut;
	
	public ChiptuneTracker() {
		Synth.startEngine(0);
		
		osc = new SawtoothOscillatorBL();
		lineOut = new LineOut();
		
		osc.output.connect(0, lineOut.input, 0);
		osc.output.connect(0, lineOut.input, 1);
		
		osc.start();
		lineOut.start();
		
		osc.frequency.set(200);
		osc.amplitude.set(0.1);
		
		Synth.sleepForTicks(400);
		
		osc.frequency.set(300);
		
		Synth.sleepForTicks(400);
		
		osc.stop();
		lineOut.stop();
		osc.delete();
		lineOut.delete();
		Synth.stopEngine();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChiptuneTracker();
	}

}

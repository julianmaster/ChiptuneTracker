package main;

import com.jsyn.JSyn;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.softsynth.jsyn.LineOut;
import com.softsynth.jsyn.SawtoothOscillatorBL;
import com.softsynth.jsyn.Synth;

public class ChiptuneTracker {
	
	SawtoothOscillatorBL osc;
	LineOut lineOut;
	
	public ChiptuneTracker() {
//		Synth.startEngine(0);
//		
//		osc = new SawtoothOscillatorBL();
//		lineOut = new LineOut();
//		
//		osc.output.connect(0, lineOut.input, 0);
//		osc.output.connect(0, lineOut.input, 1);
//		
//		osc.start();
//		lineOut.start();
//		
//		osc.frequency.set(200);
//		osc.amplitude.set(0.1);
//		
//		Synth.sleepForTicks(400);
//		
//		osc.frequency.set(300);
//		
//		Synth.sleepForTicks(400);
//		
//		osc.stop();
//		lineOut.stop();
//		osc.delete();
//		lineOut.delete();
//		Synth.stopEngine();
		
		Chanel chanel1 = new Chanel(new SineOscillator());
		Chanel chanel2 = new Chanel(new TriangleOscillator());
		
		Sample sample = new Sample();
		sample.speed = 16;
		sample.sounds = new Sound[12];
		sample.sounds[0] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample.sounds[1] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample.sounds[2] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample.sounds[3] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample.sounds[4] = new Sound(Note.D1, Instrument.INSTRUMENT_1);
		sample.sounds[5] = new Sound(Note.D1, Instrument.INSTRUMENT_1);
		sample.sounds[6] = new Sound(Note.E1, Instrument.INSTRUMENT_1);
		sample.sounds[7] = new Sound(Note.E1, Instrument.INSTRUMENT_1);
		sample.sounds[8] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		sample.sounds[9] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		sample.sounds[10] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		sample.sounds[11] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		
		
		Sample sample2 = new Sample();
		sample2.speed = 8;
		sample2.sounds = new Sound[12];
		sample2.sounds[0] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample2.sounds[1] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample2.sounds[2] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample2.sounds[3] = new Sound(Note.C1, Instrument.INSTRUMENT_1);
		sample2.sounds[4] = new Sound(Note.D1, Instrument.INSTRUMENT_1);
		sample2.sounds[5] = new Sound(Note.D1, Instrument.INSTRUMENT_1);
		sample2.sounds[6] = new Sound(Note.E1, Instrument.INSTRUMENT_1);
		sample2.sounds[7] = new Sound(Note.E1, Instrument.INSTRUMENT_1);
		sample2.sounds[8] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		sample2.sounds[9] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		sample2.sounds[10] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		sample2.sounds[11] = new Sound(Note.F1, Instrument.INSTRUMENT_1);
		
		
//		sample.sounds[0] = new Sound(Note.T1);
//		sample.sounds[1] = new Sound(Note.T2);
//		sample.sounds[2] = new Sound(Note.T3);
//		sample.sounds[3] = new Sound(Note.T4);
		
		chanel2.play(sample);
		chanel1.play(sample);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChiptuneTracker();
	}

}

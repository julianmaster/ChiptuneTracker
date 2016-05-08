package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.WhiteNoise;
import com.softsynth.shared.time.ScheduledCommand;
import com.softsynth.shared.time.TimeStamp;

public class Chanel {
	public static final int INSTRUMENTS = 8;
	public static final int VOLUME_MAX = 7;
	
	private final Chanels chanels;
	
	private CustomCircuit[] voices;
	
	private boolean start = false;
	private boolean finish = false;
	
	private int sample;
	private int sampleSpeed;
	private double sampleFrequency;
	private double lastSoundTime;
	private int soundCursor;
	
	private int UICursor;
	
	public Chanel(Chanels chanels) {
		this.chanels = chanels;

		voices = new CustomCircuit[INSTRUMENTS];
		
		add(0, new OscillatorCircuit(new SineOscillator()));
		FunctionOscillator sineSawtoothOscillator = new FunctionOscillator();
		sineSawtoothOscillator.function.set(new SineSawtoothFunction());
		add(1, new OscillatorCircuit(sineSawtoothOscillator));
		add(2, new OscillatorCircuit(new SawtoothOscillatorDPW()));
		add(3, new OscillatorCircuit(new SquareOscillatorBL()));
		add(4, new OscillatorCircuit(new DemiSquareOscillator()));
		FunctionOscillator mountainOscillator = new FunctionOscillator();
		mountainOscillator.function.set(new MoutainFunction());
		add(5, new OscillatorCircuit(mountainOscillator));
		add(6, new WhiteNoiseCircuit(new WhiteNoise()));
		add(7, new OscillatorCircuit(new TriangleOscillator()));
	}
	
	private void add(int position, CustomCircuit circuit) {
		chanels.getSynth().add(circuit);
		circuit.getOutput().connect(0, chanels.getLineOut().input, 0);
		circuit.getOutput().connect(0, chanels.getLineOut().input, 1);
		voices[position] = circuit;
	}
	
	private Synthesizer getSynthesizer(int instrument) {
		return voices[instrument].getSynthesizer();
	}
	
	public void play(Sound sound) {
		double time = chanels.getSynth().getCurrentTime();
		play(sound, 0, 16, time);
	}
	
	public void playSample(int sampleIndex) {
		this.sample = sampleIndex;
		
		Sample samplePlay = ChiptuneTracker.samples.get(sample);
		
		lastSoundTime = chanels.getSynth().getCurrentTime();
		sampleSpeed = samplePlay.speed;
		sampleFrequency = 1 / ((double)samplePlay.speed / 2);
		soundCursor = 0;
		start = true;
		finish = false;

		// Timer UI cursor
		UICursor = -1;
	}
	
	public void update() {
		if(!finish) {
			double currentTime = chanels.getSynth().getCurrentTime();
			if(currentTime > lastSoundTime) {
				Sound sound = ChiptuneTracker.samples.get(sample).sounds[soundCursor];
				if(start) {
					play(sound, soundCursor, sampleSpeed, currentTime);
					lastSoundTime = currentTime;
					start = false;
				}
				else if(sound != null) {
					lastSoundTime += sampleFrequency;
					play(sound, soundCursor, sampleSpeed, lastSoundTime);
				}
				// FIXME : Problème pour la dernière note
//				else {
//					lastSoundTime += sampleFrequency;
//					tickUICursor(lastSoundTime, soundCursor);
//				}
				
				if(soundCursor < Sample.SIZE) {
					soundCursor++;
				}
				
				if(soundCursor == ChiptuneTracker.samples.get(sample).loopStop && soundCursor != ChiptuneTracker.samples.get(sample).loopStart) {
					soundCursor = ChiptuneTracker.samples.get(sample).loopStart;
				}
				
				if(soundCursor == Sample.SIZE) {
					stop(lastSoundTime + sampleFrequency);
					tickUICursor(lastSoundTime + sampleFrequency, soundCursor);
				}
			}
		}
	}
	
	private void play(final Sound sound, final int position, int speed, double time) {
		final double frequency = Notes.getFrequency(sound.octave, sound.note);
		// Cap the value to 0.25 (1 chanel on 4 play as same time)
		final double volume = Math.min((double) sound.volume / (double) (VOLUME_MAX * Chanels.CHANELS), 0.25d);
		double samplefrequency = 1 / ((double) speed / 2);
		
		final TimeStamp start = new TimeStamp(time);
		TimeStamp end = new TimeStamp(time + samplefrequency);
		
		getSynthesizer(sound.instrument).scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				voices[sound.instrument].noteOn(frequency, volume, voices[sound.instrument].getSynthesizer().createTimeStamp());
				UICursor = position;
			}
		});
		
		getSynthesizer(sound.instrument).scheduleCommand(end, new ScheduledCommand() {
			@Override
			public void run() {
				voices[sound.instrument].noteOff(voices[sound.instrument].getSynthesizer().createTimeStamp());
				// FIXME : Voir pour déplacer le lancement du pattern suivant
				if(soundCursor == Sample.SIZE) {
					chanels.nextPattern();
				}
			}
		});
	}
	
	private void tickUICursor(double time, final int position) {
		final TimeStamp start = new TimeStamp(time);
		chanels.getSynth().scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				UICursor = position;
			}
		});
	}
	
	public int getSample() {
		return sample;
	}
	
	public void stop() {
		double time = chanels.getSynth().getCurrentTime();
		for(CustomCircuit circuit : voices) {
			circuit.noteOff(new TimeStamp(time));
			circuit.noteOff(new TimeStamp(lastSoundTime));
		}
	}
	
	private void stop(double time) {
		final TimeStamp start = new TimeStamp(time);
		chanels.getSynth().scheduleCommand(start, new ScheduledCommand() {
			@Override
			public void run() {
				finish = true;
				chanels.stopSample();
			}
		});
	}
	
	public int getSoundCursor() {
		return UICursor;
	}
}

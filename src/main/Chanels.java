package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class Chanels {
	public final static int CHANELS = 4;
	public Chanel[] chanels;
	private Synthesizer synth;
	private LineOut lineOut;
	
	private boolean playSample = false;
	
	private boolean playPattern = false;
	private int currentPattern;
	
	public Chanels() {
		synth = JSyn.createSynthesizer();
		
		lineOut = new LineOut();
		synth.add(lineOut);
		
		chanels = new Chanel[CHANELS];
		
		for(int i = 0; i < CHANELS; i++) {
			chanels[i] = new Chanel(this);
		}
		
		synth.start();
		lineOut.start();
	}
	
	/**
	 * ----------
	 * Sound
	 * ---------- 
	 */
	
	public void playSound(Sound sound) {
		chanels[0].play(sound);
	}
	
	
	
	
	/**
	 * ----------
	 * Sample
	 * ---------- 
	 */
	
	public void playSample(int sampleIndex) {
		playSample = true;
		chanels[0].playSample(-1, sampleIndex);
	}
	
	public void stopSample() {
		chanels[0].stop();
		playSample = false;
	}
	
	public void getSample() {
		chanels[0].getSample();
	}
	
	public int getSampleCursor() {
		return chanels[0].getSoundCursor();
	}
	
	public boolean isPlaySample() {
		return playSample;
	}
	
	public void setPlaySample(boolean playSample) {
		this.playSample = playSample;
	}
	
	
	
	/**
	 * ----------
	 * Pattern
	 * ---------- 
	 */
	
	public void playPattern(int patternIndex) {
		currentPattern = patternIndex;
		playPattern = true;
		next();
	}
	
	public void stopPattern() {
		for(int i = 0; i < CHANELS; i++) {
			chanels[i].stop();
		}
		playPattern = false;
	}
	
	public boolean isPlayPattern() {
		return playPattern;
	}
	
	public int getPattern() {
		return currentPattern;
	}
	
	
	
	
	
	
	
	/**
	 * ----------
	 * Update
	 * ---------- 
	 */
	
	public void update() {
		if(playSample) {
			chanels[0].update();
		}
		if(playPattern) {
			for(int i = 0; i < CHANELS; i++) {
				chanels[i].update();
			}
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * ----------
	 * Next
	 * ---------- 
	 */
	
	public void next() {
		System.out.println("next");
		if(playSample) {
			chanels[0].stop();
			playSample = false;
		}
		
		if(playPattern) {
			for(int i = 0; i < CHANELS; i++) {
				chanels[i].stop();
			}
			
			Pattern pattern = ChiptuneTracker.data.patterns.get(currentPattern);
			
			boolean finish = true;
			if(pattern.sample1 != null) {
				chanels[0].playSample(pattern.sample1, currentPattern + 1);
				finish = false;
			}
			if(pattern.sample2 != null) {
				chanels[1].playSample(pattern.sample2, currentPattern + 1);
				finish = false;
			}
			if(pattern.sample3 != null) {
				chanels[2].playSample(pattern.sample3, currentPattern + 1);
				finish = false;
			}
			if(pattern.sample4 != null) {
				chanels[3].playSample(pattern.sample4, currentPattern + 1);
				finish = false;
			}
			
			playPattern = !finish;
			if(playPattern) {
				currentPattern++;
			}
		}
	}

	
	
	
	
	
	/**
	 * ----------
	 * Getters / Setters
	 * ---------- 
	 */
	
	public Synthesizer getSynth() {
		return synth;
	}
	
	public LineOut getLineOut() {
		return lineOut;
	}
}

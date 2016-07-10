package main;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class Chanels {
	public final static int CHANELS = 4;
	private Chanel[] chanels;
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
		chanels[0].playSample(sampleIndex, -1);
	}
	
	public void stopSample() {
		chanels[0].stop();
		playSample = false;
	}
	
	public void getSample() {
		chanels[0].getSample();
	}
	
	public int getSampleCursor(int i) {
		if(i >= 0 && i < 4) {
			return chanels[i].getSoundCursor();
		}
		else {
			return -1;
		}
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
	
	public int getPatternCursor() {
		return currentPattern - 1;
	}
	
	public int getNextPattern() {
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
		if(playSample) {
			chanels[0].stop();
			playSample = false;
		}
		
		if(playPattern) {
			for(int i = 0; i < CHANELS; i++) {
				chanels[i].stop();
			}
			
			chanels[0].clear();
			chanels[1].clear();
			chanels[2].clear();
			chanels[3].clear();
			
			if(currentPattern >= ChiptuneTracker.getInstance().getData().patterns.size()) {
				playPattern = false;
				return;
			}
			Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(currentPattern);
			
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

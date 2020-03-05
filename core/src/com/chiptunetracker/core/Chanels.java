package com.chiptunetracker.core;

import com.chiptunetracker.model.Pattern;
import com.chiptunetracker.model.Sound;
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
	private boolean start = false;
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
	
	public void playSound(Sound sound, int position) {
		chanels[0].clearLastSound();
		chanels[0].play(sound, position);
	}
	
	
	
	
	/**
	 * ----------
	 * Sample
	 * ---------- 
	 */
	
	public void playSample(int sampleIndex) {
		playSample = true;
		chanels[0].clearLastSound();
		chanels[0].playSample(sampleIndex, -1, true);
	}
	
	public void stopSample() {
		chanels[0].stop();
		playSample = false;
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
	

	
	/**
	 * ----------
	 * Pattern
	 * ---------- 
	 */
	
	public void playPattern(int patternIndex) {
		currentPattern = patternIndex;
		playPattern = true;
		start = true;
		for(int i = 0; i < CHANELS; i++) {
			chanels[i].clearLastSound();
		}
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
			if(start) {
				for(int i = 0; i < CHANELS; i++) {
					chanels[i].stop();
				}
			}
			for(int i = 0; i < CHANELS; i++) {
				chanels[i].clear();
			}

			if(currentPattern >= ChiptuneTracker.getInstance().getData().patterns.size()) {
				playPattern = false;
				return;
			}
			Pattern pattern = ChiptuneTracker.getInstance().getData().patterns.get(currentPattern);
			
			boolean finish = true;
			if(pattern.sample1 != null) {
				chanels[0].playSample(pattern.sample1, currentPattern + 1, start);
				finish = false;
			}
			if(pattern.sample2 != null) {
				chanels[1].playSample(pattern.sample2, currentPattern + 1, start);
				finish = false;
			}
			if(pattern.sample3 != null) {
				chanels[2].playSample(pattern.sample3, currentPattern + 1, start);
				finish = false;
			}
			if(pattern.sample4 != null) {
				chanels[3].playSample(pattern.sample4, currentPattern + 1, start);
				finish = false;
			}
			
			playPattern = !finish;
			if(playPattern) {
				currentPattern++;
			}

			start = false;
		}
	}


	/**
	 * ----------
	 * Looped / Finished
	 * ----------
	 */
	
	public boolean allLooped() {
		boolean looped = true;
		for(int i = 0; i < 4; i++) {
			if(chanels[i] != null) {
				looped &= chanels[i].isLooped();
			}
		}
		return looped;
	}

	public boolean allFinished() {
		return chanels[0].isFinished() && chanels[1].isFinished() && chanels[2].isFinished() && chanels[3].isFinished();
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

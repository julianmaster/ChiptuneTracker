package main;

public class Chanels {
	public final static int CHANELS = 4;
	public Chanel[] chanels;
	
	private boolean playSample = false;
	
	private boolean playPattern = false;
	private int currentPattern;
	
	public Chanels() {
		chanels = new Chanel[CHANELS];
		
		for(int i = 0; i < CHANELS; i++) {
			chanels[i] = new Chanel(this);
		}
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
		chanels[0].playSample(sampleIndex);
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
		nextPattern();
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
	
	public void nextPattern() {
		if(playPattern) {
			for(int i = 0; i < CHANELS; i++) {
				chanels[i].stop();
			}
			
			Pattern pattern = ChiptuneTracker.patterns.get(currentPattern);
			
			boolean finish = true;
			if(pattern.sample1 != null) {
				chanels[0].playSample(pattern.sample1);
				finish = false;
			}
			if(pattern.sample2 != null) {
				chanels[1].playSample(pattern.sample2);
				finish = false;
			}
			if(pattern.sample3 != null) {
				chanels[2].playSample(pattern.sample3);
				finish = false;
			}
			if(pattern.sample4 != null) {
				chanels[3].playSample(pattern.sample4);
				finish = false;
			}
			
			playPattern = !finish;
			if(playPattern) {
				currentPattern++;
			}
		}
		else {
			playSample = false;
		}
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
}

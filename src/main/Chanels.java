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
	
	public void playSound(Sound sound) {
		chanels[0].play(sound);
	}
	
	public void playSample(int sampleIndex) {
		playSample = true;
		chanels[0].playSample(sampleIndex);
	}
	
	public void stopSample() {
		chanels[0].stop();
		playSample = false;
	}
	
	public boolean isPlaySample() {
		return playSample;
	}
	
	public void setPlaySample(boolean playSample) {
		this.playSample = playSample;
	}
	
	public void getSample() {
		chanels[0].getSample();
	}
	
	public int getSampleCursor() {
		return chanels[0].getSoundCursor();
	}
	
	public boolean isPlayPattern() {
		return playPattern;
	}
	
	public int getPattern() {
		return currentPattern;
	}
	
	public void nextPattern() {
		if(playPattern) {
			// Play the next pattern
		}
		else {
			playSample = false;
		}
	}
	
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

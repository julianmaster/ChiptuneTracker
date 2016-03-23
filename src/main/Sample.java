package main;

public class Sample {
	public static final int SIZE = 32;
	
	public Sound[] sounds;
	public int speed;
	public int loopStart = 0;
	public int loopStop = 0;
	
	public Sample() {
		sounds = new Sound[SIZE];
		speed = 16;
	}
}

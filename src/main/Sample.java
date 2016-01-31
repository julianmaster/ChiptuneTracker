package main;

public class Sample {
	public static final int SIZE = 32;
	
	public Sound[] sounds;
	public int speed;
	
	public Sample() {
		sounds = new Sound[SIZE];
		speed = 16;
	}
}

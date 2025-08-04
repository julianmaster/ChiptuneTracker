package io.github.julianmaster.chiptunetracker.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementArray;

public class Sample {
	public static final int SIZE = 32;

	@ElementArray
	public Sound[] sounds;

	@Attribute
	public int speed;

	@Attribute
	public int loopStart = 0;

	@Attribute
	public int loopStop = 0;

	public Sample() {
		sounds = new Sound[SIZE];
		speed = 16;
	}
}

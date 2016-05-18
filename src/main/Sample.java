package main;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementArray;

public class Sample implements Serializable {
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

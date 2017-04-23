package com.chiptunetracker.core;

import org.simpleframework.xml.Attribute;

public class Sound {
	@Attribute
	public Note note;
	
	@Attribute
	public Integer octave;
	
	@Attribute
	public Integer instrument;
	
	@Attribute
	public Integer volume;
	
	public Sound() {
		octave = null;
		note = null;
		instrument = null;
		volume = null;
	}

	public Sound(Note note, Integer octave, Integer instrument,
			Integer volume) {
		this.note = note;
		this.octave = octave;
		this.instrument = instrument;
		this.volume = volume;
	}
}

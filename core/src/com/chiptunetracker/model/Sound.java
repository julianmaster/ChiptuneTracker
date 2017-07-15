package com.chiptunetracker.model;

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

	@Attribute
	public Integer effect;
	
	public Sound() {
		octave = null;
		note = null;
		instrument = null;
		volume = null;
		effect = null;
	}

	public Sound(Note note, Integer octave, Integer instrument, Integer volume, Integer effect) {
		this.note = note;
		this.octave = octave;
		this.instrument = instrument;
		this.volume = volume;
		this.effect = effect;
	}
}

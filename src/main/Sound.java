package main;

public class Sound {
	public Note note;
	public Integer octave;
	public Integer instrument;
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

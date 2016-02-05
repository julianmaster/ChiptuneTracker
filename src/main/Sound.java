package main;

public class Sound {
	public Note note;
	public Integer octave;
	public Instrument instrument;
	public Integer volume;
	
	public Sound() {
		octave = null;
		note = null;
		instrument = null;
		volume = null;
	}

	public Sound(Note note, Integer octave, Instrument instrument,
			Integer volume) {
		this.note = note;
		this.octave = octave;
		this.instrument = instrument;
		this.volume = volume;
	}
}

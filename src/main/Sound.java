package main;

public class Sound {
	public Note note;
	public Instrument instrument;
	
	public Sound() {
		note = Note.C1;
		instrument =Instrument.INSTRUMENT_1;
	}

	public Sound(Note note, Instrument instrument) {
		this.note = note;
		this.instrument = instrument;
	}
}

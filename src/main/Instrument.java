package main;


public enum Instrument {
	INSTRUMENT_1(0), // SineOscillator
	INSTRUMENT_2(1); // TriangleOscillator
	
	public int number;

	private Instrument(int number) {
		this.number = number;
	}
}

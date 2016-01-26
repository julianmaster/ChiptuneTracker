package main;


public enum Instrument {
	INSTRUMENT_1(0),
	INSTRUMENT_2(1);
	
	public int number;

	private Instrument(int number) {
		this.number = number;
	}
}

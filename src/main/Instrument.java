package main;

import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitGenerator;

public enum Instrument {
	INSTRUMENT_1(new SineOscillator()),
	INSTRUMENT_2(new TriangleOscillator());
	
	public UnitGenerator unit;

	private Instrument(UnitGenerator unit) {
		this.unit = unit;
	}
}

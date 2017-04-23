package com.chiptunetracker.core;

import com.chiptunetracker.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Notes {
	private static Map<Pair<Integer, Note>, Double> notes = new HashMap<Pair<Integer, Note>, Double>() {{
		this.put(new Pair<>(1, Note.C), 130.81);
		this.put(new Pair<>(1, Note.C_D), 138.59);
		this.put(new Pair<>(1, Note.D), 146.83);
		this.put(new Pair<>(1, Note.D_D), 155.56);
		this.put(new Pair<>(1, Note.E), 164.81);
		this.put(new Pair<>(1, Note.F), 174.61);
		this.put(new Pair<>(1, Note.F_D), 185.00);
		this.put(new Pair<>(1, Note.G), 196.00);
		this.put(new Pair<>(1, Note.G_D), 207.65);
		this.put(new Pair<>(1, Note.A), 220.00);
		this.put(new Pair<>(1, Note.A_D), 233.08);
		this.put(new Pair<>(1, Note.B), 246.94);
		
		this.put(new Pair<>(2, Note.C), 261.63);
		this.put(new Pair<>(2, Note.C_D), 277.18);
		this.put(new Pair<>(2, Note.D), 293.66);
		this.put(new Pair<>(2, Note.D_D), 311.13);
		this.put(new Pair<>(2, Note.E), 329.63);
		this.put(new Pair<>(2, Note.F), 349.23);
		this.put(new Pair<>(2, Note.F_D), 369.99);
		this.put(new Pair<>(2, Note.G), 392.00);
		this.put(new Pair<>(2, Note.G_D), 415.30);
		this.put(new Pair<>(2, Note.A), 440.00);
		this.put(new Pair<>(2, Note.A_D), 466.16);
		this.put(new Pair<>(2, Note.B), 493.88);
		
		this.put(new Pair<>(3, Note.C), 523.25);
		this.put(new Pair<>(3, Note.C_D), 554.37);
		this.put(new Pair<>(3, Note.D), 587.33);
		this.put(new Pair<>(3, Note.D_D), 622.25);
		this.put(new Pair<>(3, Note.E), 659.26);
		this.put(new Pair<>(3, Note.F), 698.46);
		this.put(new Pair<>(3, Note.F_D), 739.99);
		this.put(new Pair<>(3, Note.G), 783.99);
		this.put(new Pair<>(3, Note.G_D), 830.61);
		this.put(new Pair<>(3, Note.A), 880.00);
		this.put(new Pair<>(3, Note.A_D), 932.33);
		this.put(new Pair<>(3, Note.B), 987.77);
		
		this.put(new Pair<>(4, Note.C), 1046.50);
		this.put(new Pair<>(4, Note.C_D), 1108.73);
		this.put(new Pair<>(4, Note.D), 1174.66);
		this.put(new Pair<>(4, Note.D_D), 1244.51);
		this.put(new Pair<>(4, Note.E), 1318.51);
		this.put(new Pair<>(4, Note.F), 1396.91);
		this.put(new Pair<>(4, Note.F_D), 1479.98);
		this.put(new Pair<>(4, Note.G), 1567.98);
		this.put(new Pair<>(4, Note.G_D), 1661.22);
		this.put(new Pair<>(4, Note.A), 1760.00);
		this.put(new Pair<>(4, Note.A_D), 1864.66);
		this.put(new Pair<>(4, Note.B), 1975.53);
	}};

	public static Double getFrequency(Integer octave, Note note) {
		return notes.get(new Pair<>(octave, note));
	}
}

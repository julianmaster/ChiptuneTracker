package io.github.julianmaster.chiptunetracker.model;

public enum Note {
	C("C"+(char)250),
	C_D("C#"),
	D("D"+(char)250),
	D_D("D#"),
	E("E"+(char)250),
	F("F"+(char)250),
	F_D("F#"),
	G("G"+(char)250),
	G_D("G#"),
	A("A"+(char)250),
	A_D("A#"),
	B("B"+(char)250);

	public String str;

	private Note(String str) {
		this.str = str;
	}
}

package main;

public enum Note {
	C("C"),
	C_D("C#"),
	D("D"),
	D_D("D#"),
	E("E"),
	F("F"),
	F_D("F#"),
	G("G"),
	G_D("G#"),
	A("A"),
	A_D("A#"),
	B("B");
	
	public String str;

	private Note(String str) {
		this.str = str;
	}
}

package main;

public enum Note {
	T1(140),
	T2(30),
	T3(240),
	T4(70),
	
	E2(82.41),
	G2(98.00),
	B2(123.47),
	
	C1(261.63),
	C1D(277.18),
	D1(293.66),
	D1D(311.13),
	E1(329.63),
	F1(349.23),
	F1D(369.99),
	G1(392.00),
	G1D(415.30),
	A1(440.00),
	A1D(466.16),
	B1(493.88);
	
	

	public double frequency;

	private Note(double frequency) {
		this.frequency = frequency;
	}
}

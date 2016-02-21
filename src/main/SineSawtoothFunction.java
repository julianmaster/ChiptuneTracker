package main;

import com.jsyn.data.Function;

public class SineSawtoothFunction implements Function {

	@Override
	public double evaluate(double input) {
		return (- 1.16635) * Math.pow(input, 3) + 1.16635 * input;
	}
}

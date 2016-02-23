package main;

import com.jsyn.data.Function;

public class SineSawtoothFunction implements Function {
	
	public static final int H = 20;

	@Override
	public double evaluate(double input) {
		return (input - (Math.tanh(((input+0.5)-Math.floor(input+0.5)-0.5)*H)/(2*Math.tanh(0.5*H))+Math.floor(input+0.5)))*3;
	}
}

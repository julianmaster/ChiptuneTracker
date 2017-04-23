package com.chiptunetracker.core;

import com.jsyn.data.Function;

public class SineSawtoothFunction implements Function {
	
	public static final int H = 20;

	@Override
	public double evaluate(double input) {
		return ((input+1)/2 - (Math.tanh((((input+1)/2+0.5)-Math.floor((input+1)/2+0.5)-0.5)*H)/(2*Math.tanh(0.5*H))+Math.floor((input+1)/2+0.5)))*2.3;
	}
}

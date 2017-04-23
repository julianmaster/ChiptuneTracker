package com.chiptunetracker.core;

import com.jsyn.data.Function;

public class MoutainFunction implements Function {

	@Override
	public double evaluate(double input) {
		if(input < 0) {
			return Math.sin(input * Math.PI) * 0.75 - 0.25;
		}
		else {
			return Math.sin(input * Math.PI) * 0.75 + 0.25;
		}
	}
}

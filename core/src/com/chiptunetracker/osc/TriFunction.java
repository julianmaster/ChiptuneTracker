package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class TriFunction implements Function {

    @Override
    public double evaluate(double input) {
        return (Math.abs(CustomMath.mod(input/2d,1d)*2d-1d)*2d-1d) * 0.7d;
    }
}

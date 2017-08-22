package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class UnevenTriFunction implements Function {

    @Override
    public double evaluate(double input) {
        double t = CustomMath.mod(input/2d,1d);
        return (((t < 0.875d) ? (t * 16d / 7d) : ((1d-t)*16d)) -1d) * 0.7d;
    }
}

package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class PulseFunction implements Function {

    @Override
    public double evaluate(double input) {
        return (CustomMath.mod(input/2,1) < 0.3125d ? 1d : -1d) * 1d/3d;
    }
}

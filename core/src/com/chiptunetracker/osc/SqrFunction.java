package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class SqrFunction implements Function {

    @Override
    public double evaluate(double input) {
        return (CustomMath.mod(input/2d,1) < 0.5d ? 1d : -1d) * 1d/3d;
    }
}

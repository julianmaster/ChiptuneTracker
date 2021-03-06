package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class DetunedTriFunction1 implements Function {

    @Override
    public double evaluate(double input) {
        return (Math.abs(CustomMath.mod(input,2d)-1d) - 0.5d);
    }
}

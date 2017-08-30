package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class DetunedTriFunction2 implements Function {

    @Override
    public double evaluate(double input) {
        return ((Math.abs(CustomMath.mod((input * 127d / 128d),2d) - 1d) - 0.5d) / 2d - 0.1d) * 0.7d;
    }
}

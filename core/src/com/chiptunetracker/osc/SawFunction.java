package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class SawFunction implements Function {

    @Override
    public double evaluate(double input) {
        return (CustomMath.mod(input/2,1)-0.5d) * 0.9d;
    }
}

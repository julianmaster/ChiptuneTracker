package com.chiptunetracker.osc;

import com.chiptunetracker.util.CustomMath;
import com.jsyn.data.Function;

public class DemiTriFunction implements Function {

    @Override
    public double evaluate(double input) {
        input=input*2;
        return (Math.abs(CustomMath.mod(input,2)-1)-0.5 + (Math.abs(CustomMath.mod((input*0.5),2)-1)-0.5)/2-0.1) * 0.7;
    }
}

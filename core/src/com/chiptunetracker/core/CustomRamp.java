package com.chiptunetracker.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitVariablePort;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by Julien on 04/07/2017.
 */
public class CustomRamp extends UnitFilter {

    public UnitVariablePort current;

    public UnitInputPort time;
    public UnitInputPort valueReach;
    private int framesLeft;

    public CustomRamp() {
        addPort(time = new UnitInputPort(1, "Time", 1.0));
        addPort(valueReach = new UnitInputPort(1, "Value Reach", 1.0));
        addPort(current = new UnitVariablePort("Current"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();
        double[] inputs = input.getValues();
        double currentTime = time.getValues()[0];
        double currentValue = current.getValue();
        double inputValue = currentValue;

        for (int i = start; i < limit; i++) {
            inputValue = inputs[i];
            double x;
            if (inputValue != current.getValue()) {
                x = framesLeft;
                // Calculate coefficients.
//                double currentSlope = x * ((3 * a * x) + (2 * b));

                framesLeft = (int) (getSynthesisEngine().getFrameRate() * currentTime);
                if (framesLeft < 1) {
                    framesLeft = 1;
                }
                x = framesLeft;
                // Calculate coefficients.
//                d = inputValue;
//                double xsq = x * x;
//                b = ((3 * currentValue) - (currentSlope * x) - (3 * d)) / xsq;
//                a = (currentSlope - (2 * b * x)) / (3 * xsq);
//                previousInput = inputValue;
            }

            if (framesLeft > 0) {
                x = --framesLeft;
                // Cubic polynomial. c==0
//                currentValue = (x * (x * ((x * a) + b))) + d;
            }

            outputs[i] = currentValue;
        }

        current.setValue(currentValue);
    }
}

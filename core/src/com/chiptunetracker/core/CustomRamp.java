package com.chiptunetracker.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitVariablePort;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by Julien on 04/07/2017.
 */
public class CustomRamp extends UnitFilter {

    public UnitInputPort amplitude;
    public UnitInputPort time;
    public UnitInputPort valueReach;
    private double a = 0.0d;
    private double b = 0.25d;

    public CustomRamp() {
        addPort(time = new UnitInputPort(1, "Time", 0.0d));
        addPort(valueReach = new UnitInputPort(1, "Value Reach", 0.25d));
        addPort(amplitude = new UnitInputPort(1, "Amplitude", 0.25d));
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            if (valueReach.getValue() != amplitude.getValue()) {
                double delta = getSynthesisEngine().getFramePeriod();
                time.set(time.getValue() - delta);

                // Calculate coefficients.
                a = -((valueReach.get() - amplitude.get()) / (time.get()));
                b = valueReach.get() - a * time.get();
            }

            amplitude.set(b);

            System.out.println(amplitude.getValue());

            outputs[i] = amplitude.getValue();
        }
    }
}

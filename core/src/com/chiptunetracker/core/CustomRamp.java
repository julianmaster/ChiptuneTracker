package com.chiptunetracker.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitVariablePort;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by Julien on 04/07/2017.
 */
public class CustomRamp extends UnitFilter {

    public UnitInputPort startValue;
    public UnitInputPort endValue;
    public UnitInputPort duration;
    public UnitInputPort time;
    private Double a = 0.0d;
    private Double b = 0.25d;

    public CustomRamp() {
        addPort(startValue = new UnitInputPort(1, "Start", 0.25d));
        addPort(endValue = new UnitInputPort(1, "End", 0.25d));
        duration = new UnitInputPort(1, "Duration", 1.0d);
        addPort(time = new UnitInputPort(1, "Time", 0.0d));
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            a = -(endValue.get() - startValue.get()) / duration.get();
            b = endValue.get();

            double delta = getSynthesisEngine().getFramePeriod();
            time.set(time.getValue() - delta);

            System.out.println(a*time.get() + b);

            outputs[i] = a*time.get() + b;
        }
    }
}

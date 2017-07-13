package com.chiptunetracker.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitVariablePort;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by Julien on 04/07/2017.
 */
public class CustomRamp extends UnitFilter {

    public static final int COUNT_PERIOD_GROUP = 2;

    public UnitInputPort startValue;
    public UnitInputPort endValue;
    public UnitInputPort duration;
    public UnitInputPort time;

    public boolean isStart = false;
    private int count = 2;

    private double a = 0.0d;
    private double b = 0.25d;
    private double currentValue = b;

    public CustomRamp() {
        addPort(startValue = new UnitInputPort(1, "Start", 0.25d));
        addPort(endValue = new UnitInputPort(1, "End", 0.25d));
        duration = new UnitInputPort(1, "Duration", 1.0d);
        addPort(time = new UnitInputPort(1, "Time", 0.0d));
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();

        if(isStart) {
            isStart = false;
            count = COUNT_PERIOD_GROUP;
        }

        if(count > 0) {
            count--;
//            System.out.println("bad");
//            System.out.println(currentValue);
            double part = ((startValue.get() - currentValue) / ((double)limit - (double)start)) / COUNT_PERIOD_GROUP;

            for (int i = start; i < limit; i++) {
                currentValue = currentValue + part;
                outputs[i] = currentValue;
            }
        }
        else {
//            System.out.println("good");
//            System.out.println(currentValue);

            for (int i = start; i < limit; i++) {
                a = -(endValue.get() - startValue.get()) / duration.get();
                b = endValue.get();

                double delta = getSynthesisEngine().getFramePeriod();
                time.set(time.get() - delta);

                currentValue = a*time.get() + b;

                outputs[i] = currentValue;
            }
        }
    }
}

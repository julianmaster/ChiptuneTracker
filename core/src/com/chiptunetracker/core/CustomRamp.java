package com.chiptunetracker.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitVariablePort;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by Julien on 04/07/2017.
 */
public class CustomRamp extends UnitFilter {

    private final double maxAmplitude;

    public UnitInputPort amplitude;
    public UnitInputPort time;
    public UnitInputPort valueReach;
    private double a = 0.0d;
    private double b = 1.0d;
    private int framesLeft;

    public CustomRamp(double maxAmplitude) {
        this.maxAmplitude = maxAmplitude;
        addPort(time = new UnitInputPort(1, "Time", 0.0d));
        addPort(valueReach = new UnitInputPort(1, "Value Reach", 1.0d));
        addPort(amplitude = new UnitInputPort(1, "Amplitude", 1.0d));
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();
        double currentTime = time.getValue();
        double currentValue = amplitude.getValue();

        double inputValue;
        for (int i = start; i < limit; i++) {
            inputValue = inputs[i];
//            double x;
            double delta = 0d;
            if (valueReach.getValue() != amplitude.getValue()) {

                delta = (double)getSynthesisEngine().getFrameRate() * getSynthesisEngine().getFramePeriod();
                System.out.println(delta);
                time.set(time.getValue() - delta);
//                framesLeft = (int) (getSynthesisEngine().getFrameRate() * currentTime);
//                if (framesLeft < 1) {
//                    framesLeft = 1;
//                }
//                x = framesLeft;

                // Calculate coefficients.
                a = (valueReach.get() - amplitude.get()) / (time.get());
                b = valueReach.get() - a * time.get();
            }

//            if (framesLeft > 0) {
//                x = --framesLeft;
//                currentValue = a*x + b;

//                currentValue = a*delta + b;
                amplitude.set(a*delta + b);
//            }

            outputs[i] = amplitude.getValue() * maxAmplitude;
        }

        System.out.println(amplitude.getValue());
//        amplitude.set(currentValue);
    }
}

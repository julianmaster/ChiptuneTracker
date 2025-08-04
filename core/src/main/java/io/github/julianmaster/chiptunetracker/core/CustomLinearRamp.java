package io.github.julianmaster.chiptunetracker.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitVariablePort;
import com.jsyn.unitgen.UnitFilter;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Created by Julien on 28/07/2017.
 */
public class CustomLinearRamp extends UnitFilter {
    /** Time in seconds to get to the input value. */
    public UnitInputPort time;
    public UnitVariablePort current;

    public UnitInputPort isStart;
    public UnitInputPort startValue;

    private double source;
    private double phase;
    private double target;
    private double timeHeld = 0.0;
    private double rate = 1.0;

    public CustomLinearRamp() {
        addPort(time = new UnitInputPort("Time"));
        addPort(current = new UnitVariablePort("Current"));
        addPort(isStart = new UnitInputPort("Is start"));
        addPort(startValue = new UnitInputPort(("Start Value")));
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();
        double currentInput = input.getValues()[0];
        double currentValue = current.getValue();

        if(isStart.get() > UnitGenerator.FALSE) {
            isStart.set(UnitGenerator.FALSE);
            double value = startValue.getValue();
            current.set(value);
            currentValue = value;
            currentInput = value;
        }

        // If input has changed, start new segment.
        // Equality check is OK because we set them exactly equal below.
        if (currentInput != target)
        {
            source = currentValue;
            phase = 0.0;
            target = currentInput;
        }

        if (currentValue == target) {
            // at end of ramp
            for (int i = start; i < limit; i++) {
                outputs[i] = currentValue;
            }
        } else {
            // in middle of ramp
            double currentTime = time.getValues()[0];
            // Has time changed?
            if (currentTime != timeHeld) {
                rate = convertTimeToRate(currentTime);
                timeHeld = currentTime;
            }

            for (int i = start; i < limit; i++) {
                if (phase < 1.0) {
                    /* Interpolate current. */
                    currentValue = source + (phase * (target - source));
                    phase += rate;
                } else {
                    currentValue = target;
                }
                outputs[i] = currentValue;
            }
        }

        current.setValue(currentValue);
    }
}

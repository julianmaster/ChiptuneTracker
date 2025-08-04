package io.github.julianmaster.chiptunetracker.osc;

import com.jsyn.data.Function;
import com.jsyn.engine.SynthesisEngine;

public class NoiseFunction implements Function {

    double lastx = 0;
    double sample = 0;
    double lsample = 0;
    double tscale= noteToHZ(63) / SynthesisEngine.DEFAULT_FRAME_RATE;

    @Override
    public double evaluate(double input) {
        double scale = (input - lastx) / tscale;
        lsample = sample;
        sample = (lsample + scale * (Math.random() * 2 - 1)) / (1 + scale);
        lastx = input;
        return Math.min(Math.max((lsample + sample) * 4 / 3 * (1.75 - scale), -1), 1) * 0.7;
    }

    public static double noteToHZ(int note) {
        return 440 * Math.pow(2,(note - 33) / 12);
    }
}

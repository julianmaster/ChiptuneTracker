package main;

import com.jsyn.unitgen.UnitOscillator;

public class DemiSquareOscillator extends UnitOscillator {
	
	@Override
    public void generate(int start, int limit) {
        double[] frequencies = frequency.getValues();
        double[] amplitudes = amplitude.getValues();
        double[] outputs = output.getValues();

        // Variables have a single value.
        double currentPhase = phase.getValue();

        for (int i = start; i < limit; i++) {
            /* Generate sawtooth phasor to provide phase for square generation. */
            double phaseIncrement = convertFrequencyToPhaseIncrement(frequencies[i]);
            currentPhase = incrementWrapPhase(currentPhase, phaseIncrement);

            double ampl = amplitudes[i];
            // Either full negative or positive amplitude.
            outputs[i] = (currentPhase < 0.5) ? -ampl : ampl;
        }

        // Value needs to be saved for next time.
        phase.setValue(currentPhase);
    }
}

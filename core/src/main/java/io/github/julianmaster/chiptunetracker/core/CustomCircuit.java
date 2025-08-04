package io.github.julianmaster.chiptunetracker.core;

import io.github.julianmaster.chiptunetracker.model.Sound;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.UnitVoice;
import com.softsynth.shared.time.TimeStamp;

public abstract class CustomCircuit extends Circuit implements UnitVoice {

    public abstract void usePreset(Sound lastSound, int presetIndex, double frequency, double amplitude, double duration, TimeStamp timeStamp);
}

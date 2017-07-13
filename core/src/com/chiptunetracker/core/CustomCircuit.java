package com.chiptunetracker.core;

import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.UnitVoice;
import com.softsynth.shared.time.TimeStamp;

public abstract class CustomCircuit extends Circuit implements UnitVoice {

    public abstract void usePreset(int presetIndex, double frequency, double amplitude, double duration, TimeStamp timeStamp);
}

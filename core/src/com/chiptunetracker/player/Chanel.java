package com.chiptunetracker.player;

import com.chiptunetracker.core.Chanels;
import com.chiptunetracker.model.Notes;
import com.chiptunetracker.model.Sound;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 * Created by Julien on 04/06/2017.
 */
public class Chanel {
    public static final float DEFAULT_FREQUENCY = 440.0f;
    public static final int INSTRUMENTS = 8;
    public static final int VOLUME_MAX = 7;

    AudioContext ac = new AudioContext();

    private WavePlayer[] voices;
    private Gain[] gains;

    public Chanel() {
        voices = new WavePlayer[INSTRUMENTS];
        gains = new Gain[INSTRUMENTS];

        Glide g = new Glide(ac);

        add(0, new WavePlayer(ac, DEFAULT_FREQUENCY, Buffer.SINE));
        add(1, new WavePlayer(ac, DEFAULT_FREQUENCY, new SineSawtoothBuffer().getDefault()));
        add(2, new WavePlayer(ac, DEFAULT_FREQUENCY, Buffer.SAW));
        add(3, new WavePlayer(ac, DEFAULT_FREQUENCY, Buffer.SQUARE));
        add(4, new WavePlayer(ac, DEFAULT_FREQUENCY, new DemiSquareBuffer().getDefault()));
        add(5, new WavePlayer(ac, DEFAULT_FREQUENCY, new MountainBuffer().getDefault()));
        add(6, new WavePlayer(ac, DEFAULT_FREQUENCY, Buffer.NOISE));
        add(7, new WavePlayer(ac, DEFAULT_FREQUENCY, Buffer.TRIANGLE));

        ac.start();
    }

    public void add(int position, WavePlayer wavePlayer) {
        Gain gain = new Gain(ac, 1, 0f);
        gain.addInput(wavePlayer);

        voices[position] = wavePlayer;
        gains[position] = gain;

        ac.out.addInput(gain);
    }

    public void play(Sound sound) {
        playNote(sound, 0, 16);
    }

    private void playNote(final Sound sound, final int position, int speed) {
        final float volume = (float)Math.min((double) sound.volume / (double) (VOLUME_MAX * Chanels.CHANELS), 0.25d);

        voices[sound.instrument].setFrequency(Notes.getFrequency(sound.octave, sound.note).floatValue());
        gains[sound.instrument].setGain(volume);
//        gains[sound.instrument].
    }
}

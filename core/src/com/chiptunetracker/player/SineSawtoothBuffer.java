package com.chiptunetracker.player;

import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;

/**
 * Created by Julien on 04/06/2017.
 */
public class SineSawtoothBuffer extends BufferFactory {

    public static final int H = 20;

    @Override
    public Buffer generateBuffer(int var1) {
        Buffer var2 = new Buffer(var1);

        for(int var3 = 0; var3 < var1; ++var3) {
//            var2.buf[var3] = (float)var3 / (float)var1 * 2.0F - 1.0F;
            var2.buf[var3] = (((float)var3/(float)var1+1f)/2f - ((float)Math.tanh(((((float)var3/(float)var1+1f)/2f + 0.5f) - (float)Math.floor(((float)var3/(float)var1+1f)/2f + 0.5f) - 0.5f)*H)/(2f*(float)Math.tanh(0.5f*H)) + (float)Math.floor(((float)var3/(float)var1 + 1f)/2f + 0.5f)))*2.3f;
        }

        return var2;
    }

    @Override
    public String getName() {
        return "Sine Sawtooth";
    }
}

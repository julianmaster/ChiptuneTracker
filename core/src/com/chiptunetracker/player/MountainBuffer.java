package com.chiptunetracker.player;

import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;

/**
 * Created by Julien on 05/06/2017.
 */
public class MountainBuffer extends BufferFactory {

    @Override
    public Buffer generateBuffer(int var1) {
        Buffer var2 = new Buffer(var1);
        int var3 = var1 / 2;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            var2.buf[var4] = (float)Math.sin(((float)var4 * 1f/((float)var1/2f) - 1f) * (float)Math.PI) * 0.75f - 0.25f;
        }

        for(var4 = var3; var4 < var1; ++var4) {
            var2.buf[var4] = (float)Math.sin((((float)var4 - (float)var3) * 1f/((float)var1/2f)) * (float)Math.PI) * 0.75f + 0.25f;
        }

        return var2;
    }

    @Override
    public String getName() {
        return "Mountain";
    }
}

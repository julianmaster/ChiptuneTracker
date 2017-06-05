package com.chiptunetracker.player;

import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;

/**
 * Created by Julien on 04/06/2017.
 */
public class DemiSquareBuffer extends BufferFactory {

    @Override
    public Buffer generateBuffer(int var1) {
        Buffer var2 = new Buffer(var1);
        int var3 = var1 / 4;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            var2.buf[var4] = 1.0F;
        }

        for(var4 = var3; var4 < var1; ++var4) {
            var2.buf[var4] = -1.0F;
        }

        return var2;
    }

    @Override
    public String getName() {
        return "Demi Square";
    }
}

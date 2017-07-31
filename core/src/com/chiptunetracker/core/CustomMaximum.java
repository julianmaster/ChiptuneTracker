package com.chiptunetracker.core;

import com.jsyn.unitgen.Maximum;

/**
 * Created by Julien on 28/07/2017.
 */
public class CustomMaximum extends Maximum {

    @Override
    public void generate(int i, int i1) {
        super.generate(i, i1);

        System.out.println(this.output.get());
    }
}

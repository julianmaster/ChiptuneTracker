package com.chiptunetracker.util;

public class CustomMath {

    public static double mod(double a, double b) {
        return (a % b + b) % b;
    }
}

package com.toptrumps.core.utils;

import java.util.Random;

public class RandomGenerator {

    private RandomGenerator() {
    }

    public static int getInteger(int min, int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt((max - min) + 1) + min;
    }

}

package com.nure.kravchenko.student.reference.service.utils;

import java.util.Random;

public class RandomUtils {

    private RandomUtils() {
        //private constructor for RandomUtils class.
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

}

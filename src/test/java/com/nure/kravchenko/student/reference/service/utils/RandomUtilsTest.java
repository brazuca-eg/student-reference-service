package com.nure.kravchenko.student.reference.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomUtilsTest {

    @Test
    void getRandomNumber() {
        int randomNumber = RandomUtils.getRandomNumber(100, 200);
        assertTrue(randomNumber > 99 && randomNumber < 201);
    }

}
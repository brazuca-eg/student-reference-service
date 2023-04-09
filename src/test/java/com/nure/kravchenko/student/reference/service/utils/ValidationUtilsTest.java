package com.nure.kravchenko.student.reference.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationUtilsTest {

    private static final String CORRECT_EMAIL = "email_em@nure.ua";

    private static final String INCORRECT_EMAIL = "email_em@gmail.ua";

    @Test
    void checkValidEmailAddress() {
        assertTrue(ValidationUtils.isValidEmailAddress(CORRECT_EMAIL));
    }

    @Test
    void checkInvalidEmailAddress() {
        assertFalse(ValidationUtils.isValidEmailAddress(INCORRECT_EMAIL));
    }

}
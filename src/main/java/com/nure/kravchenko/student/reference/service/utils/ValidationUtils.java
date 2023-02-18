package com.nure.kravchenko.student.reference.service.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@nure.ua";

    private ValidationUtils(){
        //private constructor for ValidationUtils class.
    }

    public static boolean isValidEmailAddress(String email) {
        return Pattern.compile(EMAIL_REGEX_PATTERN)
                .matcher(email)
                .matches();
    }

}

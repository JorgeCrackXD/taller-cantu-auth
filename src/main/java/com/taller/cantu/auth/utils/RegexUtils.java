package com.taller.cantu.auth.utils;

import com.taller.cantu.auth.exception.RegexValidationException;

import java.util.regex.Pattern;

public class RegexUtils {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static boolean patternMatches(String stringToValidate, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(stringToValidate)
                .matches();
    }

    public static void isValidEmail(String email) {
        if(!patternMatches(email, EMAIL_REGEX)) {
            throw new RegexValidationException("Invalid email format");
        }
    }

    public static void isValidPassword(String password) {
        if(!patternMatches(password, PASSWORD_REGEX)) {
            throw new RegexValidationException("Invalid password format");
        }
    }
}

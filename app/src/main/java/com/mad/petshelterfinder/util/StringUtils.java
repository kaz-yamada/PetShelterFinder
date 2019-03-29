package com.mad.petshelterfinder.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for string operations
 */
public class StringUtils {
    private final static int CHAR_INDEX = 1;
    private final static int MIN_PASSWORD_LENGTH = 6;

    /**
     * Converts the first letter of string to a capital letter
     *
     * @param message string to modify
     * @return new string with capital
     */
    public static String capitaliseString(String message) {
        return message.substring(0, CHAR_INDEX).toUpperCase()
                + message.substring(CHAR_INDEX, message.length());
    }


    /**
     * @param email value to check
     * @return returns true if valid email, false if otherwise
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Base method of checking if a password is valid
     *
     * @param password value to check
     * @return true if password is valid, false if not
     */
    public static boolean isPasswordValid(String password) {
        return password.length() > MIN_PASSWORD_LENGTH;
    }

    public static void logMessage(String message) {
        Log.d("LOG_D", message);
    }
}

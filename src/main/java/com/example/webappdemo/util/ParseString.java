package com.example.webappdemo.util;

public class ParseString {

    /**
     * Static method to parse the string date needed for the api
     * dd-mm-yyyy length is 10
     * mm-yyyy length is 7
     * yyyy length is 4
     * <p>
     * Based on the length will the results be set
     *
     * @param date
     * @return
     */
    public static String[] getIndividualValues(String date) {
        String[] result = new String[3];
        int length = date.length();

        switch (length) {
            case 10: {
                result[0] = date.substring(0, 2);
                result[1] = date.substring(3, 5);
                result[2] = date.substring(6);
                break;
            }
            case 7: {
                result[0] = null;
                result[1] = date.substring(0, 2);
                result[2] = date.substring(3);
                break;
            }
            case 4: {
                result[0] = null;
                result[1] = null;
                result[2] = date;
                break;
            }
            default: {
                /*
                if default was reached something must have been wrong,
                year is set to null too, so it will result in an error displayed
                 */
                result[0] = null;
                result[1] = null;
                result[2] = null;
                break;
            }
        }

        return result;
    }

    /**
     * Check the location and date string for invalid chars. These will cause the program to fail.
     * Note: the input word is small this won't take much memory to loop through.
     *
     * @param input String date or location
     * @return boolean true if match or else false
     */
    public static boolean checkForInvalidCharacters(String input) {
        char[] chars = {'~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '+', '_', '=',
                '{', '[', '}', ']', ';', ':', '"', ',', '<', '>', '.', '?', '/', '|', '\\'};

        for (int i = 0; i <= input.length() - 1; i++) {
            char letter = input.charAt(i);

            for (int j = 0; j <= chars.length - 1; j++) {
                if (letter == chars[j]) {
                    return true; // break the loop and return true, because we have a match
                }
            }
        }
        return false;
    }
}

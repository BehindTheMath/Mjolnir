package io.behindthemath.mjolnir.utils;

/**
 * Created by Behind The Math on 3/16/2017.
 */
public class Utils {
    /**
     * Searches the specified array of chars for the specified value.
     *
     * @param charArray The array to be searched
     * @param value The value to be searched for
     * @return The index of the value searched for, if it is found in the array; otherwise -1.
     */
    public static int arraySearch(char[] charArray, char value) {
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == value) return i;
        }
        return -1;
    }

    /**
     * Searches the specified array of {@link String}s for the specified value.
     *
     * @param stringArray The array to be searched
     * @param value The value to be searched for
     * @return The index of the value searched for, if it is found in the array; otherwise -1.
     */
    public static int arraySearch(String[] stringArray, String value) {
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(value)) return i;
        }
        return -1;
    }
}

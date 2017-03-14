package io.behindthemath.mjolnir.attack;

import java.util.Arrays;

/**
 * Heuristic version of the brute force attack, designed to use known strings
 * with additional random values. For example, if the user knows they probably
 * used 'password123' but can't remember the exact numbers
 * 
 * @author Antony Lees
 */
public class HeuristicBruteForce extends BruteForce {

    private String word;

    public HeuristicBruteForce(String word, char[] characterSet, int guessLength) {
        super(characterSet, guessLength);
        this.word = word;
    }

    @Override
    public char[] getNextAttempt() {
        char[] chars = super.getNextAttempt();
        char[] result = Arrays.copyOf(word.toCharArray(), word.length() + chars.length);
        System.arraycopy(chars, 0, result, word.length(), chars.length);
        return result;
    }

}

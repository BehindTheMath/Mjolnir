package io.behindthemath.mjolnir.attack;

import java.util.Arrays;

import static io.behindthemath.mjolnir.utils.Utils.arraySearch;

/**
 * Brute force attack implementation of {@link Attack}.
 * 
 * @author Antony Lees
 */
public class BruteForce implements Attack {
    private final char[] characterSet;
    // An array of ints, each one corresponding to the index of a character in characterSet
    private int[] currentGuessIndexes;
    private final int characterSetMaxIndex;

    public BruteForce(final char[] characterSet, final int guessLength) {
        this(characterSet, guessLength, null);
    }

    public BruteForce(final char[] characterSet, final int guessLength, final String lastAttempt) {
        this.characterSet = characterSet;
        characterSetMaxIndex = characterSet.length - 1;

        if (lastAttempt == null) {
            currentGuessIndexes = new int[guessLength];
            Arrays.fill(currentGuessIndexes, 0);
        } else {
            currentGuessIndexes = parseAttemptToStartFrom(lastAttempt);
        }
    }

    /**
     * Calculates the next guess
     */
    private void increment() {
        // Start from the right-most character
        int index = currentGuessIndexes.length - 1;
        while (index >= 0) {
            // If we haven't yet tried every character from the character set in this character position
            if (currentGuessIndexes[index] != characterSetMaxIndex) {
                // Try the next character
                currentGuessIndexes[index]++;
                break;
            } else {
                // Otherwise, if we have already tried every character from the character set in this character position
                if (index != 0) {
                    /* If the current character position isn't the first position (left-most), that means we haven't
                     * tried every possible permutation for this guess length. So reset current character position to
                     * the first character from the character set, and move to the next position to the left.
                     */
                    currentGuessIndexes[index] = 0;
                    index--;
                } else {
                    // If we've already tried every permutation for this guess length, go to the next one
                    currentGuessIndexes = new int[currentGuessIndexes.length + 1];
                    Arrays.fill(currentGuessIndexes, 0);
                    break;
                }
            }
        }
    }

    @Override
    public char[] getNextAttempt() {
        increment();
        return buildCurrentGuess();
    }

    /**
     * Builds the next guess into a {@link String}.
     *
     * @return The current guess.
     */
    private char[] buildCurrentGuess() {
        char[] guess = new char[currentGuessIndexes.length];
        for(int i = 0; i < guess.length; i++) {
            guess[i] = characterSet[currentGuessIndexes[i]];
        }
        return guess;
    }

    /**
     * Parses a {@link String} representing the last attempt, into an array of {@code int}s for {@code currentGuessIndexes}.
     *
     * @param lastAttempt A {@link String} representing the last attempt.
     * @return An array of {@code int}s for {@code currentGuessIndex}.
     */
    private int[] parseAttemptToStartFrom(final String lastAttempt) {
        int[] currentGuessIndexesBuffer = new int[lastAttempt.length()];
        for (int i = 0; i < lastAttempt.length(); i++) {
            currentGuessIndexesBuffer[i] = arraySearch(characterSet, lastAttempt.charAt(i));
        }
        return currentGuessIndexesBuffer;
    }
}

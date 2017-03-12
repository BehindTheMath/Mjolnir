package io.behindthemath.mjolnir.attack;

import java.util.Arrays;

/**
 * Brute force attack implementation of {@link Attack}.
 * 
 * @author Antony Lees
 */
public class BruteForce implements Attack {
    private char[] characterSet;
    private char[] currentGuess;

    public BruteForce(final char[] characterSet, final int guessLength) {
        this(characterSet, guessLength, null);
    }

    public BruteForce(char[] characterSet, int guessLength, final String lastAttempt) {
        this.characterSet = characterSet;

        if (lastAttempt == null) {
            currentGuess = new char[guessLength];
            Arrays.fill(currentGuess, this.characterSet[0]);
        } else {
            currentGuess = lastAttempt.toCharArray();
        }
    }

    private void increment() {
        int index = currentGuess.length - 1;
        while (index >= 0) {
            if (currentGuess[index] == characterSet[characterSet.length - 1]) {
                if (index == 0) {
                    currentGuess = new char[currentGuess.length + 1];
                    Arrays.fill(currentGuess, characterSet[0]);
                    break;
                } else {
                    currentGuess[index] = characterSet[0];
                    index--;
                }
            } else {
                currentGuess[index] = characterSet[arraySearch(characterSet, currentGuess[index]) + 1];
                break;
            }
        }
    }

    @Override
    public synchronized String getNextAttempt() {
        increment();
        return String.valueOf(currentGuess);
    }

    /**
     * Searches the specified array of chars for the specified value.
     *
     * @param charArray The array to be searched
     * @param charToFind The value to be searched for
     * @return The index of the value searched for, if it is found in the array; otherwise -1.
     */
    private int arraySearch(char[] charArray, char charToFind) {
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == charToFind) return i;
        }
        return -1;
    }
}

package io.behindthemath.mjolnir.attack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Behind The Math on 3/4/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BruteForce.class)
public class BruteForceTest {
    private final char[] characterSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w',
            'x','y','z'};
    private int minGuessLength = 4;
    private int maxGuessLength = minGuessLength;
    private final String INCREMENT_METHOD = "increment";
    private final String CURRENT_GUESS_INDEXES_FIELD = "currentGuessIndexes";

    @Test
    public void testIncrement() throws Exception {
        final BruteForce bruteForce = new BruteForce(characterSet, minGuessLength, maxGuessLength);
        Whitebox.invokeMethod(bruteForce, INCREMENT_METHOD);
        final int[] currentGuessIndexes = Whitebox.getInternalState(bruteForce, CURRENT_GUESS_INDEXES_FIELD);

        assertArrayEquals(new int[]{0, 0, 0, 1}, currentGuessIndexes);
    }

    @Test
    public void testIncrement_whenCurrentGuessIndexesReachesCharacterSetMaxIndex_itShouldCarryOverAndResetCurrentPosition() throws Exception {
        final BruteForce bruteForce = new BruteForce(characterSet, minGuessLength, maxGuessLength);
        Whitebox.setInternalState(bruteForce, CURRENT_GUESS_INDEXES_FIELD, new int[]{0, 0, 0, 25});
        Whitebox.invokeMethod(bruteForce, INCREMENT_METHOD);
        final int[] currentGuessIndexes = Whitebox.getInternalState(bruteForce, CURRENT_GUESS_INDEXES_FIELD);

        assertArrayEquals(new int[]{0, 0, 1, 0}, currentGuessIndexes);
    }

    @Test
    public void testIncrement_whenCurrentGuessIndexesReachesCharacterSetMaxIndexForAllPositions_itShouldAddAPosition() throws Exception {
        final BruteForce bruteForce = new BruteForce(characterSet, minGuessLength, maxGuessLength);
        Whitebox.setInternalState(bruteForce, CURRENT_GUESS_INDEXES_FIELD, new int[]{25, 25, 25, 25});
        Whitebox.invokeMethod(bruteForce, INCREMENT_METHOD);
        final int[] currentGuessIndexes = Whitebox.getInternalState(bruteForce, CURRENT_GUESS_INDEXES_FIELD);

        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, currentGuessIndexes);
    }

    @Test
    public void testParseAttemptToStartFrom() throws Exception {
        String PARSE_ATTEMPT_TO_START_FROM_METHOD = "parseAttemptToStartFrom";

        final BruteForce bruteForce = new BruteForce(characterSet, minGuessLength, maxGuessLength);
        final int[] currentGuessIndexesBuffer = Whitebox.invokeMethod(bruteForce, PARSE_ATTEMPT_TO_START_FROM_METHOD, "abcd");

        assertArrayEquals(new int[]{0, 1, 2, 3}, currentGuessIndexesBuffer);
    }
}
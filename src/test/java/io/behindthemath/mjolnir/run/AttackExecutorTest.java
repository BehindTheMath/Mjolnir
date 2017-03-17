package io.behindthemath.mjolnir.run;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.attack.BruteForce;
import io.behindthemath.mjolnir.source.Source;
import io.behindthemath.mjolnir.source.keystore.KeystoreKeySource;
import io.behindthemath.mjolnir.source.keystore.KeystoreSource;
import io.behindthemath.mjolnir.utils.Stopwatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Behind The Math on 3/1/2017.
 */
public class AttackExecutorTest {
    private final String TEST_KEYSTORE_PASSWORD = "test";
    private int minGuessLength;
    private int maxGuessLength;
    private final int numberOfWorkers = 4;
    private final int reportEvery = 20000;
    private final String KEYSTORE_FILE_PATH = "Test keystore.jks";

    @Test
    public void testAttackAgainstKeystore() throws Exception {
        minGuessLength = TEST_KEYSTORE_PASSWORD.length();
        maxGuessLength = minGuessLength;
        final char[] characterSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w',
                'x','y','z'};
        final String lastAttempt = null;

        final Source source = new KeystoreSource(KEYSTORE_FILE_PATH);
        source.setup();
        final Attack attack = new BruteForce(characterSet, minGuessLength, maxGuessLength, lastAttempt);
        final AttackExecutor attackExecutor = new AttackExecutor(attack, source, numberOfWorkers, reportEvery);

        final Stopwatch stopwatch = new Stopwatch().start();
        final String result = attackExecutor.start();
        stopwatch.stop().printTime(TimeUnit.SECONDS);

        System.out.println("\n" + "Password = " + result);
        assertEquals(TEST_KEYSTORE_PASSWORD, result);
    }

    @Test
    public void testAttackAgainstKeystoreKey() throws Exception {
        final String TEST_KEY_PASSWORD = "test1";
        final String TEST_KEY_NAME = "test key";
        minGuessLength = TEST_KEY_PASSWORD.length();
        maxGuessLength = minGuessLength;
        final char[] characterSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
                'u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
        final String lastAttempt = "s1aaa";

        final Source source = new KeystoreKeySource(KEYSTORE_FILE_PATH, TEST_KEYSTORE_PASSWORD, TEST_KEY_NAME);
        source.setup();
        final Attack attack = new BruteForce(characterSet, minGuessLength, maxGuessLength, lastAttempt);
        final AttackExecutor attackExecutor = new AttackExecutor(attack, source, numberOfWorkers, reportEvery);

        final Stopwatch stopwatch = new Stopwatch().start();
        final String result = attackExecutor.start();
        stopwatch.stop().printTime(TimeUnit.SECONDS);

        System.out.println("\n" + "Password = " + result);
        assertEquals(TEST_KEY_PASSWORD, result);
    }
}
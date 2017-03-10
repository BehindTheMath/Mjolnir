package io.behindthemath.mjolnir.run;

import io.behindthemath.mjolnir.TimedTest;
import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.attack.BruteForce;
import io.behindthemath.mjolnir.source.Source;
import io.behindthemath.mjolnir.source.keystore.KeystoreKeySource;
import io.behindthemath.mjolnir.source.keystore.KeystoreSource;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Behind The Math on 3/1/2017.
 */
public class AttackExecutorTest extends TimedTest {
    private final String TEST_KEYSTORE_PASSWORD = "test";
    private int guessLength;
    private final int numberOfWorkers = 4;
    private final int reportEvery = 20000;

    @Test
    public void testAttackAgainstKeystore() throws Exception {
        guessLength = TEST_KEYSTORE_PASSWORD.length();
        final char[] characterSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w',
                'x','y','z'};

        Source source = new KeystoreSource("Test keystore.jks");
        source.setup();
        Attack attack = new BruteForce(characterSet, guessLength);
        AttackExecutor attackExecutor = new AttackExecutor(attack, source, numberOfWorkers, reportEvery);

        markStart();
        String result = attackExecutor.start();
        markEnd();

        System.out.println("\n" + "Password = " + result);
        assertEquals(TEST_KEYSTORE_PASSWORD, result);
    }
}
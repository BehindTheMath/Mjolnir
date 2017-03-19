package io.behindthemath.mjolnir.run;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.attack.BruteForce;
import io.behindthemath.mjolnir.source.Source;
import io.behindthemath.mjolnir.source.keystore.KeystoreKeySource;
import io.behindthemath.mjolnir.source.keystore.KeystoreSource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Behind The Math on 3/16/2017.
 */
public class AttackCallableTest {
    private char[] characterSet;
    private final int minGuessLength = 4;
    private final int maxGuessLength = minGuessLength;
    private String lastAttempt;

    private final int threadNumber = 0;
    private final int reportEvery = 20000;
    private Attack attack;

    private final String KEYSTORE_FILE_PATH = "test_keystore.jks";
    private final String TEST_KEYSTORE_PASSWORD = "test";

    @Test
    public void testCall_withKeystoreSource() throws Exception {
        characterSet = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w',
                'x','y','z'};
        lastAttempt = "taaa";
        attack = new BruteForce(characterSet, minGuessLength, maxGuessLength, lastAttempt);

        final Source source = new KeystoreSource(KEYSTORE_FILE_PATH);
        source.setup();

        final AttackCallable attackCallable = new AttackCallable(threadNumber, reportEvery, attack, source);
        final String result = attackCallable.call();

        assertEquals(TEST_KEYSTORE_PASSWORD, result);
    }

    @Test
    public void testCall_withKeystoreKeySource() throws Exception {
        characterSet = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
                'u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
        lastAttempt = "tdaaa";
        final String TEST_KEY_NAME = "Test key";
        final String TEST_KEY_PASSWORD = "test1";

        final Source source = new KeystoreKeySource(KEYSTORE_FILE_PATH, TEST_KEYSTORE_PASSWORD, TEST_KEY_NAME);
        source.setup();

        attack = new BruteForce(characterSet, minGuessLength, maxGuessLength, lastAttempt);
        final AttackCallable attackCallable = new AttackCallable(threadNumber, reportEvery, attack, source);
        final String result = attackCallable.call();

        assertEquals(TEST_KEY_PASSWORD, result);
    }
}
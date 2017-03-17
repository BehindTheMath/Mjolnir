package io.behindthemath.mjolnir;

import io.behindthemath.mjolnir.source.Source;
import io.behindthemath.mjolnir.source.keystore.KeystoreKeySource;
import io.behindthemath.mjolnir.source.keystore.KeystoreSource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

/**
 * Created by Behind The Math on 2/28/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Main.class)
public class MainTest {
    private static final String PARSE_PARAMS_METHOD = "parseArgs";
    private static final String SOURCE_FIELD = "source";

    private final String TEST_PASSWORD = "password";
    private final String TEST_KEY_NAME = "keyname";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testDoMain_withKeystore() throws Exception {
        final String DO_MAIN_METHOD = "doMain";
        final boolean result = Whitebox.invokeMethod(new Main(), DO_MAIN_METHOD, (Object) (new String[]{"-s", "keystore",
                "-f", "Test keystore.jks", "-t", "4", "-n", "20000", "-g", "4", "-c", "abcdefghijklmnopqrstuvwxyz", "-l",
                "oaaa"}));
        assertTrue(result);
    }

    @Test
    public void testDoMain_withKey() throws Exception {
        final String DO_MAIN_METHOD = "doMain";
        final boolean result = Whitebox.invokeMethod(new Main(), DO_MAIN_METHOD, (Object) (new String[]{"-s", "key",
                "-f", "Test keystore.jks", "-p", "test", "-k", "Test key", "-t", "4", "-n", "20000", "-g", "4", "-c", "abcdefghijklmnopqrstuvwxyz1234567890", "-l",
                "taaaa"}));
        assertTrue(result);
    }

    @Test
    public void testParseArgs_whenArgsHas_s_FlagAndKeystore_sourceShouldBeInstanceofKeystoreSource() throws Exception {
        final String[] args = {"-s", "keystore"};

        final Main main = new Main();
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
        final Source source = Whitebox.getInternalState(main, SOURCE_FIELD);
        assertTrue(source instanceof KeystoreSource);
    }

    @Test
    public void testParseArgs_whenArgsHas_s_FlagAndKey_sourceShouldBeInstanceofKeystoreKeySource() throws Exception {
        final String[] args = {"-s", "key"};

        final Main main = new Main();
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
        final Source source = Whitebox.getInternalState(main, Source.class);
        assertTrue(source instanceof KeystoreKeySource);
    }

    @Test
    public void testParseArgs_whenArgsHas_s_FlagAndOther_illegalArgumentExceptionShouldBeThrown() throws Exception {
        final String[] args = {"-s", "other"};

        final Main main = new Main();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid argument for -s");
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
    }

    @Test
    public void testParseArgs_whenArgsHas_s_FlagAndKeystoreAnd_p_Flag_illegalArgumentExceptionShouldBeThrown() throws Exception {
        final String[] args = {"-s", "keystore", "-p", TEST_PASSWORD};

        final Main main = new Main();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("-p cannot be used with \'-s keystore\'.");
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
    }

    @Test
    public void testParseArgs_whenArgsHas_s_FlagAndKeystoreAnd_k_Flag_illegalArgumentExceptionShouldBeThrown() throws Exception {
        final String[] args = {"-s", "keystore", "-k", TEST_KEY_NAME};

        final Main main = new Main();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("-k cannot be used with \'-s keystore\'.");
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
    }

    @Test
    public void testParseArgs_fieldsShouldBeSet() throws Exception {
        final String KEY_FILE_PATH_FIELD = "keystoreFilePath";
        final String TEST_KEYSTORE_FILE_PATH = "testFilePath";

        final String KEYSTORE_PASSWORD_FIELD = "keystorePassword";

        final String KEY_NAME_FIELD = "keyName";

        final String NUMBER_OF_WORKERS_FIELD = "numberOfWorkers";
        final int TEST_NUMBER_OF_WORKERS = 10;

        final String REPORT_EVERY_FIELD = "reportEvery";
        final int TEST_REPORT_EVERY = 100000;

        final String LAST_ATTEMPT_FIELD = "lastAttempt";
        final String TEST_LAST_ATTEMPT = "test";

        final String GUESS_LENGTH_FIELD = "guessLength";
        final int TEST_GUESS_LENGTH = 5;

        final String CHARACTER_SET_FIELD = "characterSet";
        final char[] TEST_CHARACTER_SET = {'a', 'b', 'c', 'd'};


        final String[] args = {"-s", "key", "-f", TEST_KEYSTORE_FILE_PATH, "-p", TEST_PASSWORD, "-k", TEST_KEY_NAME,
                "-t", Integer.toString(TEST_NUMBER_OF_WORKERS), "-n", Integer.toString(TEST_REPORT_EVERY),
                "-l", TEST_LAST_ATTEMPT, "-g", Integer.toString(TEST_GUESS_LENGTH), "-c", String.valueOf(TEST_CHARACTER_SET)};

        final Main main = new Main();
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
        final Source source = Whitebox.getInternalState(main, "source");

        final String keystoreFilePath = Whitebox.getInternalState(source, KEY_FILE_PATH_FIELD);
        assertEquals(TEST_KEYSTORE_FILE_PATH, keystoreFilePath);

        final String keystorePassword = Whitebox.getInternalState(source, KEYSTORE_PASSWORD_FIELD);
        assertEquals(TEST_PASSWORD, keystorePassword);

        final String keyName = Whitebox.getInternalState(source, KEY_NAME_FIELD);
        assertEquals(TEST_KEY_NAME, keyName);

        final int numberOfWorkers = Whitebox.getInternalState(main, NUMBER_OF_WORKERS_FIELD);
        assertEquals(TEST_NUMBER_OF_WORKERS, numberOfWorkers);

        final int reportEvery = Whitebox.getInternalState(main, REPORT_EVERY_FIELD);
        assertEquals(TEST_REPORT_EVERY, reportEvery);

        final String lastAttempt = Whitebox.getInternalState(main, LAST_ATTEMPT_FIELD);
        assertEquals(TEST_LAST_ATTEMPT, lastAttempt);

        final int guessLength = Whitebox.getInternalState(main, GUESS_LENGTH_FIELD);
        assertEquals(TEST_GUESS_LENGTH, guessLength);

        final char[] characterSet = Whitebox.getInternalState(main, CHARACTER_SET_FIELD);
        assertArrayEquals(TEST_CHARACTER_SET, characterSet);
    }
}
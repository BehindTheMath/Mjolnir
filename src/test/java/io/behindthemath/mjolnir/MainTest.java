package io.behindthemath.mjolnir;

import io.behindthemath.mjolnir.run.PasswordNotFoundException;
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
    final String DO_MAIN_METHOD = "doMain";

    private final String TEST_KEYSTORE_FILE_PATH = "Test keystore.jks";
    private final String TEST_KEYSTORE_PASSWORD = "test";

    private final String FAKE_TEST_PASSWORD = "password";
    private final String FAKE_TEST_KEY_NAME = "keyname";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testDoMain_withKeystore() throws Exception {
        final boolean result = Whitebox.invokeMethod(new Main(), DO_MAIN_METHOD, (Object) (new String[]{"-s", "keystore",
                "-f", TEST_KEYSTORE_FILE_PATH, "-t", "4", "-n", "20000", "-m", "4", "-c", "abcdefghijklmnopqrstuvwxyz", "-l",
                "oaaa"}));
        assertTrue(result);
    }

    @Test
    public void testDoMain_withKey() throws Exception {
        final boolean result = Whitebox.invokeMethod(new Main(), DO_MAIN_METHOD, (Object) (new String[]{"-s", "key",
                "-f", TEST_KEYSTORE_FILE_PATH, "-p", TEST_KEYSTORE_PASSWORD, "-k", "Test key", "-t", "4", "-n", "20000", "-m", "4", "-c", "abcdefghijklmnopqrstuvwxyz1234567890", "-l",
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
        final String[] args = {"-s", "keystore", "-p", FAKE_TEST_PASSWORD};

        final Main main = new Main();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("-p cannot be used with \'-s keystore\'.");
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
    }

    @Test
    public void testParseArgs_whenArgsHas_s_FlagAndKeystoreAnd_k_Flag_illegalArgumentExceptionShouldBeThrown() throws Exception {
        final String[] args = {"-s", "keystore", "-k", FAKE_TEST_KEY_NAME};

        final Main main = new Main();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("-k cannot be used with \'-s keystore\'.");
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
    }

    @Test
    public void testParseArgs_fieldsShouldBeSet() throws Exception {
        final String KEY_FILE_PATH_FIELD = "keystoreFilePath";
        final String FAKE_TEST_KEYSTORE_FILE_PATH = "testFilePath";

        final String KEYSTORE_PASSWORD_FIELD = "keystorePassword";

        final String KEY_NAME_FIELD = "keyName";

        final String NUMBER_OF_WORKERS_FIELD = "numberOfWorkers";
        final int TEST_NUMBER_OF_WORKERS = 10;

        final String REPORT_EVERY_FIELD = "reportEvery";
        final int TEST_REPORT_EVERY = 100000;

        final String LAST_ATTEMPT_FIELD = "lastAttempt";
        final String TEST_LAST_ATTEMPT = "test";

        final String MIN_GUESS_LENGTH_FIELD = "minGuessLength";
        final int TEST_MIN_GUESS_LENGTH = 5;

        final String CHARACTER_SET_FIELD = "characterSet";
        final char[] TEST_CHARACTER_SET = {'a', 'b', 'c', 'd'};


        final String[] args = {"-s", "key", "-f", FAKE_TEST_KEYSTORE_FILE_PATH, "-p", FAKE_TEST_PASSWORD, "-k", FAKE_TEST_KEY_NAME,
                "-t", Integer.toString(TEST_NUMBER_OF_WORKERS), "-n", Integer.toString(TEST_REPORT_EVERY),
                "-l", TEST_LAST_ATTEMPT, "-m", Integer.toString(TEST_MIN_GUESS_LENGTH), "-c", String.valueOf(TEST_CHARACTER_SET)};

        final Main main = new Main();
        Whitebox.invokeMethod(main, PARSE_PARAMS_METHOD, (Object) args);
        final Source source = Whitebox.getInternalState(main, "source");

        final String keystoreFilePath = Whitebox.getInternalState(source, KEY_FILE_PATH_FIELD);
        assertEquals(FAKE_TEST_KEYSTORE_FILE_PATH, keystoreFilePath);

        final String keystorePassword = Whitebox.getInternalState(source, KEYSTORE_PASSWORD_FIELD);
        assertEquals(FAKE_TEST_PASSWORD, keystorePassword);

        final String keyName = Whitebox.getInternalState(source, KEY_NAME_FIELD);
        assertEquals(FAKE_TEST_KEY_NAME, keyName);

        final int numberOfWorkers = Whitebox.getInternalState(main, NUMBER_OF_WORKERS_FIELD);
        assertEquals(TEST_NUMBER_OF_WORKERS, numberOfWorkers);

        final int reportEvery = Whitebox.getInternalState(main, REPORT_EVERY_FIELD);
        assertEquals(TEST_REPORT_EVERY, reportEvery);

        final String lastAttempt = Whitebox.getInternalState(main, LAST_ATTEMPT_FIELD);
        assertEquals(TEST_LAST_ATTEMPT, lastAttempt);

        final int minGuessLength = Whitebox.getInternalState(main, MIN_GUESS_LENGTH_FIELD);
        assertEquals(TEST_MIN_GUESS_LENGTH, minGuessLength);

        final char[] characterSet = Whitebox.getInternalState(main, CHARACTER_SET_FIELD);
        assertArrayEquals(TEST_CHARACTER_SET, characterSet);
    }

    @Test
    public void testDoMain_forKeystore_withWrongArguments_KeystoreExceptionShouldBeThrown() throws Exception {
        final int TEST_NUMBER_OF_WORKERS = 1;
        final int TEST_REPORT_EVERY = 0;
        final String TEST_LAST_ATTEMPT = "zzzy";
        final int TEST_MIN_GUESS_LENGTH = 4;
        final String TEST_CHARACTER_SET = "abcdefghijklmnopqrstuvwxyz";

        final String[] args = {"-s", "keystore", "-f", TEST_KEYSTORE_FILE_PATH, "-t", Integer.toString(TEST_NUMBER_OF_WORKERS),
                "-n", Integer.toString(TEST_REPORT_EVERY), "-l", TEST_LAST_ATTEMPT, "-m", Integer.toString(TEST_MIN_GUESS_LENGTH),
                "-c", TEST_CHARACTER_SET};

        final Main main = new Main();

        exception.expect(PasswordNotFoundException.class);
        exception.expectMessage("Password could not be found with the selected options.");
        Whitebox.invokeMethod(main, DO_MAIN_METHOD, (Object) args);
    }

}
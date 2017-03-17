package io.behindthemath.mjolnir;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.attack.BruteForce;
import io.behindthemath.mjolnir.run.AttackExecutor;
import io.behindthemath.mjolnir.source.Source;
import io.behindthemath.mjolnir.source.keystore.KeystoreException;
import io.behindthemath.mjolnir.source.keystore.KeystoreKeySource;
import io.behindthemath.mjolnir.source.keystore.KeystoreSource;
import io.behindthemath.mjolnir.utils.Stopwatch;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import static io.behindthemath.mjolnir.utils.Utils.arraySearch;

/**
 * Runs the attack program
 * 
 * @author Antony Lees
 */
public class Main {
    // The source to attack
    private Source source;

    // Number of concurrent worker threads
    private int numberOfWorkers = 4;

    // If the program was previously run and interrupted, this will hold the last attempt, so we can pick up from where we left off
    private String lastAttempt;

    // Report progress every x attempts, per thread
    private int reportEvery = 20000;

    // How long the password is
    private int minGuessLength = 0;
    private int maxGuessLength = 0;

    // Set of characters to try
    private char[] characterSet;

    public static void main(String[] args) {
        if (args.length == 0) {
            displayParams();
            System.exit(0);
        }

        boolean result = false;
        try {
            result = new Main().doMain(args);
        } catch (IllegalArgumentException | FileNotFoundException | KeystoreException e) {
            displayErrorAndParams(e.getMessage());
        }

        // Exit with error code 0 if successful, otherwise exit with error code 1
        System.exit(result ? 0 : 1);

    }

    /**
     * Actually parses the command line arguments and runs the attack.
     *
     * @param args The command line arguments.
     *
     * @return {@code true} if successful; {@code false} if not.
     */
    private boolean doMain(String[] args) throws FileNotFoundException {
        final Attack attack;
        final Stopwatch stopwatch;
        final String result;

        parseArgs(args);
        validateState(args);

        // Set up the attack
        attack = new BruteForce(characterSet, minGuessLength, maxGuessLength, lastAttempt);

        // Set up the source
        source.setup();

        // Run the attack
        final AttackExecutor attackExecutor = new AttackExecutor(attack, source, numberOfWorkers, reportEvery);

        stopwatch = new Stopwatch().start();
        result = attackExecutor.start();

        stopwatch.stop().printTime(TimeUnit.SECONDS);

        if (result != null) {
            System.out.println("\n" + "Password found: " + result);
            return true;
        } else {
            System.out.println("\n" + "Unknown error occurred.");
            return false;
        }
    }

    /**
     * Parses the command line arguments
     *
     * @param args The command line arguments.
     */
    private void parseArgs(String[] args) throws IllegalArgumentException {
        // Check that there's an -s argument
        if (!"-s".equals(args[0])) throw new IllegalArgumentException("The first argument must be the -s flag.");

        // If there's an odd number of arguments, something is wrong
        if (args.length % 2 == 1) throw new IllegalArgumentException("Error parsing arguments.");

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-s":
                    if ("keystore".equals(args[i + 1])) {
                        source = new KeystoreSource();
                    } else if ("key".equals(args[i + 1])) {
                        source = new KeystoreKeySource();
                    } else {
                        throw new IllegalArgumentException("Invalid argument for -s");
                    }
                    break;
                case "-p":
                    if (!(source instanceof KeystoreKeySource)) {
                        throw new IllegalArgumentException("-p cannot be used with '-s keystore'.");
                    }
                    ((KeystoreKeySource) source).setKeystorePassword(args[i + 1]);
                    break;
                case "-k":
                    if (!(source instanceof KeystoreKeySource)) {
                        throw new IllegalArgumentException("-k cannot be used with '-s keystore'.");
                    }
                    ((KeystoreKeySource) source).setKeyName(args[i + 1]);
                    break;
                case "-f":
                    source.setKeystoreFilePath(args[i + 1]);
                    break;
                case "-t":
                    try {
                        numberOfWorkers = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("The -t flag must be passed a valid number.");
                    }
                    break;
                case "-n":
                    try {
                        reportEvery = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("The -n flag must be passed a valid number.");
                    }
                    break;
                case "-l":
                    lastAttempt = args[i + 1];
                    break;
                case "-m":
                    try {
                        minGuessLength = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("The -m flag must be passed a valid number.");
                    }
                    break;
                case "-x":
                    try {
                        maxGuessLength = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("The -x flag must be passed a valid number.");
                    }
                    break;
                case "-c":
                    characterSet = args[i + 1].toCharArray();
                    break;
                default:
                    displayParams();
                    System.exit(1);
            }
        }
    }

    /**
     * Validates the state once all the command line arguments are parsed.
     */
    private void validateState(String[] args) {
        if (characterSet == null) throw new IllegalArgumentException("The -c flag must be used to set the character set.");
        if (minGuessLength == 0) throw new IllegalArgumentException("The -m flag must be used to set the password length.");
        if (arraySearch(args, "-f") == -1) {
            throw new IllegalArgumentException("The -f flag must be used to set the file path for the keystore file.");
        }

        if (source instanceof KeystoreKeySource) {
            if (arraySearch(args, "-p") == -1) {
                throw new IllegalArgumentException("When using the -s key option, the -p flag must be used to set the password for the keystore file.");
            }
            if (arraySearch(args, "-k") == -1) {
                throw new IllegalArgumentException("When using the -s key option, the -k flag must be used to set the key name to attempt.");
            }
        }

        if ((maxGuessLength > 0) && (maxGuessLength < minGuessLength)) throw new IllegalArgumentException("maxGuessLength cannot be less than minGuessLength.");
        // If maxGuessLength was not set, set it to be the same as minGuessLength.
        if (maxGuessLength == 0) maxGuessLength = minGuessLength;
    }

    /**
     * Display the available command line parameters.
     */
    private static void displayParams() {
        System.out.println("Mjolnir v0.1\t(c) 2017 Behind The Math\n");
        System.out.println("Parameters:");
        System.out.println("-s [keystore | key]" + "\t\t" + "Determines which password is being brute forced: the keystore or a key inside the keystore. This argument must be first.");
        System.out.println("-p password" + "\t\t\t\t" + "The password for the keystore. Required if you want to brute-force the password of a key.");
        System.out.println("-k keyname" + "\t\t\t\t" + "The name of the key to brute-force.");
        System.out.println("-f filepath" + "\t\t\t\t" + "The path to the keystore file. Can be relative to the working directory.");
        System.out.println("-t threads" + "\t\t\t\t" + "The number of concurrent threads to use (default is 4).");
        System.out.println("-n numAttempts" + "\t\t\t" + "Log the progress every numAttempts for each thread (default is 20000). Set to 0 to disable logging.");
        System.out.println("-l lastattempt" + "\t\t\t" + "The last attempt already tried. The program will start after that attempt.");
        System.out.println("-m minGuessLength" + "\t\t\t" + "The minimum guess length to attack.");
        System.out.println("-x minGuessLength" + "\t\t\t" + "The maximum guess length to attempt (default is the same as minGuessLength).");
        System.out.println("-c \"characterSet\"" + "\t\t" + "The set of possible characters to try.");
    }

    private static void displayErrorAndParams(String error) {
        System.out.println("Error: " + error + "\n");
        displayParams();
    }
}

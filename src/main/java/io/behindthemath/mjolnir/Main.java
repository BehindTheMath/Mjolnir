package io.behindthemath.mjolnir;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.attack.BruteForce;
import io.behindthemath.mjolnir.run.AttackRunner;
import io.behindthemath.mjolnir.source.Source;
import io.behindthemath.mjolnir.source.keystore.KeystoreKeySource;
import io.behindthemath.mjolnir.source.keystore.KeystoreSource;

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
    private int guessLength = 0;

    // Set of characters to try
    private char[] characterSet;

    public static void main(String[] args) {
        if (args.length == 0) {
            displayParams();
            System.exit(0);
        }

        new Main().doMain(args);
    }


    private void doMain(String[] args) {
        parseArgs(args);
        validateState();

        // Set up the attack
        Attack attack = new BruteForce(characterSet, guessLength, lastAttempt);

        // Set up the source
        source.setup();

        // Run the attack
        AttackRunner attackRunner = new AttackRunner(attack, source, numberOfWorkers, reportEvery);
        attackRunner.start();
    }

    /**
     * Parses the command line arguments
     *
     * @param args
     */
    private void parseArgs(String[] args) {
        // Check that there's an -s argument
        if (!"-s".equals(args[0])) displayErrorAndParams("The first argument must be the -s flag.");

        // If there's an odd number of arguments, something is wrong
        if (args.length % 2 == 1) displayErrorAndParams("Error parsing arguments.");

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-s":
                    if ("keystore".equals(args[i + 1])) {
                        source = new KeystoreSource();
                    } else if ("key".equals(args[i + 1])) {
                        source = new KeystoreKeySource();
                    } else {
                        displayErrorAndParams("Invalid argument for -s");
                    }
                    break;
                case "-p":
                    if (!(source instanceof KeystoreKeySource)) {
                        displayErrorAndParams("-p cannot be used with '-s keystore'.");
                    }
                    ((KeystoreKeySource) source).setKeystorePassword(args[i + 1]);
                    break;
                case "-k":
                    if (!(source instanceof KeystoreKeySource)) {
                        displayErrorAndParams("-k cannot be used with '-s keystore'.");
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
                        displayErrorAndParams("The -t flag must be passed a valid number.");
                    }
                    break;
                case "-n":
                    try {
                        reportEvery = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        displayErrorAndParams("The -n flag must be passed a valid number.");
                    }
                    break;
                case "-l":
                    lastAttempt = args[i + 1];
                    break;
                case "-g":
                    try {
                        guessLength = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        displayErrorAndParams("The -g flag must be passed a valid number.");
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
    private void validateState() {
        if (characterSet == null) displayErrorAndParams("The -c flag must be used to set the character set.");
        if (guessLength == 0) displayErrorAndParams("The -g flag must be used to set the password length.");
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
        System.out.println("-g guesslength" + "\t\t\t" + "The guess length to attack.");
        System.out.println("-c \"characterSet\"" + "\t\t" + "The set of possible characters to try.");
    }

    private static void displayErrorAndParams(String error) {
        System.out.println("Error: " + error + "\n");
        displayParams();
        System.exit(1);
    }
}

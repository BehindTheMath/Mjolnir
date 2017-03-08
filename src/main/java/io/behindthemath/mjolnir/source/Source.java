package io.behindthemath.mjolnir.source;

/**
 * Defines the source to be cracked; e.g. a keystore.
 * 
 * @author Antony Lees
 */
public interface Source {
    /**
     * Called to setup the source before any attempts are made.
     */
    void setup();

    /**
     * Attempt to crack the password.
     *
     * @return {@code true} if the attempt succeeded
     */
    boolean attempt(String attempt);

    void setKeystoreFilePath(String keystoreFilePath);
}

package io.behindthemath.mjolnir.source;

import java.io.FileNotFoundException;

/**
 * Defines the source to be cracked; e.g. a keystore.
 * 
 * @author Antony Lees
 */
public interface Source {
    /**
     * Called to setup the source before any attempts are made.
     */
    void setup() throws FileNotFoundException;

    /**
     * Attempt to crack the password.
     *
     * @return {@code true} if the attempt succeeded
     */
    boolean attempt(char[] attempt);

    void setKeystoreFilePath(String keystoreFilePath);
}

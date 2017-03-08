package io.behindthemath.mjolnir.source;

/**
 * Defines the source to be cracked eg a keystore
 * 
 * @author Antony Lees
 */
public interface Source {
    /**
     * Called to setup the source before any attempts are made
     */
    void setup();

    /**
     * Attempt to crack the password.  Return true if it worked
     * @return true if the attempt succeeded
     */
    boolean attempt(String attempt);
}

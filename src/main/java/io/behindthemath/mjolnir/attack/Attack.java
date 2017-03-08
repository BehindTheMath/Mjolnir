package io.behindthemath.mjolnir.attack;

/**
 * Defines an attack type eg brute force attack
 * 
 * @author Antony Lees
 */
public interface Attack {
    /**
     * Get the next password attempt. This is intended to be atomic, so if this
     * involves processing to generate the next attempt, this should also be
     * done here
     *
     * @return the next password attempt
     */
    String getNextAttempt();
}

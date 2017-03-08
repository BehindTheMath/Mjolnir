package io.behindthemath.mjolnir.run;

/**
 * Allows control between threads
 * <p>
 * Taken from http://armstrong.craig.free.fr/eBooks/SAMS/SAMS%20Java%20Thread%20Programming.pdf page 316
 * 
 * @author Antony Lees
 */
class BooleanLock {
    private boolean value;

    BooleanLock(boolean initialValue) {
        value = initialValue;
    }

    BooleanLock() {
        this(false);
    }

    synchronized void setValue(boolean newValue) {
        if (newValue != value) {
            value = newValue;
            notifyAll();
        }
    }

    synchronized boolean waitToSetTrue(long msTimeout)
            throws InterruptedException {

        boolean success = waitUntilFalse(msTimeout);
        if (success) {
            setValue(true);
        }

        return success;
    }

    synchronized boolean waitToSetFalse(long msTimeout)
            throws InterruptedException {

        boolean success = waitUntilTrue(msTimeout);
        if (success) {
            setValue(false);
        }

        return success;
    }

    synchronized boolean isTrue() {
        return value;
    }

    synchronized boolean isFalse() {
        return !value;
    }

    synchronized boolean waitUntilTrue(long msTimeout)
            throws InterruptedException {

        return waitUntilStateIs(true, msTimeout);
    }

    synchronized boolean waitUntilFalse(long msTimeout)
            throws InterruptedException {

        return waitUntilStateIs(false, msTimeout);
    }

    synchronized boolean waitUntilStateIs(boolean state, long msTimeout)
            throws InterruptedException {

        if (msTimeout == 0L) {
            while (value != state) {
                // wait indefinitely until notified
                wait();
            }

            // condition has finally been met
            return true;
        }

        // only wait for the specified amount of time
        long endTime = System.currentTimeMillis() + msTimeout;
        long msRemaining = msTimeout;

        while ((value != state) && (msRemaining > 0L)) {
            wait(msRemaining);
            msRemaining = endTime - System.currentTimeMillis();
        }

        // May have timed out, or may have met value, calculate return value.
        return (value == state);
    }
}
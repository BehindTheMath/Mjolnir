package io.behindthemath.mjolnir.run;

/**
 * Created by Behind The Math on 3/17/2017.
 */
public class PasswordNotFoundException extends RuntimeException {
    public PasswordNotFoundException(Throwable cause) {
        super(cause);
    }

    public PasswordNotFoundException(String message) {
        super(message);
    }
}

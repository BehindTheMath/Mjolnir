package io.behindthemath.mjolnir.source.keystore;

/**
 * General exception class for keystores
 * 
 * @author Antony Lees
 */
class KeystoreException extends RuntimeException {

    private static final long serialVersionUID = 7837507479354995645L;

    KeystoreException(Throwable cause) {
        super(cause);
    }

}
package io.behindthemath.mjolnir.source.keystore;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Loads a key from a given keystore
 * 
 * @author Antony Lees
 */
class KeyLoader {

    /**
     * Loads a key from the given keystore.
     *
     * @param keystore the keystore containing the key
     * @param keyAlias the alias of the key
     * @param password the password attempt
     * @return The key, or {@code null} if the password is incorrect or key could not be not found.
     */
    static Key loadKey(KeyStore keystore, String keyAlias, String password) {

        try {
            // get my private key
            Key key = keystore.getKey(keyAlias, password.toCharArray());
            return key;
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e) {
            // let it return null
        }
        return null;

    }

}

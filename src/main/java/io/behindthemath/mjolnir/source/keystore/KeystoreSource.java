package io.behindthemath.mjolnir.source.keystore;

import java.io.FileNotFoundException;
import java.security.KeyStore;

import io.behindthemath.mjolnir.source.Source;

/**
 * Source of key attacks. Keystore must be loadable.
 * 
 * @author Antony Lees
 */
public class KeystoreSource implements Source {
    private KeystoreLoader keystoreLoader;
    private String keystoreFilePath;

    public KeystoreSource() {}

    public KeystoreSource(String keystoreFilePath) {
        this.keystoreFilePath = keystoreFilePath;
    }

    @Override
    public void setup() throws FileNotFoundException {
        keystoreLoader = new KeystoreLoader(keystoreFilePath);
    }

    @Override
    public boolean attempt(char[] attempt) {
        if (keystoreLoader == null) {
            throw new IllegalStateException("setup() was not called.");
        }

        final KeyStore keystore = keystoreLoader.loadKeystore(attempt);
        // return true if the key is not null (ie null key == not found)
        return keystore != null;
    }

    @Override
    public void setKeystoreFilePath(String keystoreFilePath) {
        this.keystoreFilePath = keystoreFilePath;
    }
}

package io.behindthemath.mjolnir.source.keystore;

import java.security.KeyStore;

import io.behindthemath.mjolnir.source.Source;

/**
 * Source of key attacks.  Keystore must be loadable
 * 
 * @author Antony Lees
 */
public class KeystoreSource implements Source {

    private KeystoreLoader keystoreLoader;

    private String keystoreFilePath;

    @Override
    public void setup() {
        keystoreLoader = new KeystoreLoader();
    }

    @Override
    public boolean attempt(String attempt) {
        KeyStore keystore = keystoreLoader.loadKeystore(keystoreFilePath, attempt);
        // return true if the key is not null (ie null key == not found)
        return keystore != null;
    }

    public void setKeystoreFilePath(String keystoreFilePath) {
        this.keystoreFilePath = keystoreFilePath;
    }

}

package io.behindthemath.mjolnir.source.keystore;

import java.security.Key;
import java.security.KeyStore;

import io.behindthemath.mjolnir.source.Source;

/**
 * Source for keystore attacks
 * 
 * @author Antony Lees
 */
public class KeystoreKeySource implements Source {
    private KeyStore keyStore;

    private String keystorePassword;
    private String keystoreFilePath;
    private String keyName;

    public KeystoreKeySource() {}

    public KeystoreKeySource(String keystoreFilePath, String keystorePassword, String keyName) {
        this.keystoreFilePath = keystoreFilePath;
        this.keystorePassword = keystorePassword;
        this.keyName = keyName;
    }

    @Override
    public void setup() {
        KeystoreLoader keystoreLoader = new KeystoreLoader(keystoreFilePath);
        keyStore = keystoreLoader.loadKeystore(keystorePassword.toCharArray());
    }

    @Override
    public boolean attempt(char[] attempt) {
        final Key key = KeyLoader.loadKey(keyStore, keyName, String.valueOf(attempt));
        // return true if the key is not null (ie null key == not found)
        return key != null;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    @Override
    public void setKeystoreFilePath(String keystoreFilePath) {
        this.keystoreFilePath = keystoreFilePath;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}

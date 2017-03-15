package io.behindthemath.mjolnir.source.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Loads keystores.
 * 
 * @author Antony Lees
 */
class KeystoreLoader {
    private final File file;

    KeystoreLoader(String keystoreFilePath) {
        file = new File(keystoreFilePath);
    }

    /**
     * Loads the keystore with the given password.
     *
     * @param keystorePassword the password attempt
     * @return The keystore, or null if the keystore cannot be opened.
     */
    KeyStore loadKeystore(char[] keystorePassword) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(is, keystorePassword);
            return keystore;
        } catch (java.security.cert.CertificateException | KeyStoreException | NoSuchAlgorithmException | FileNotFoundException e) {
            throw new KeystoreException(e);
        } catch (IOException e) {
            if (e.getCause().getMessage().contains("Password verification failed")) {
                return null;
            }
            throw new KeystoreException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // at least we tried
                e.printStackTrace();
            }
        }
    }
}

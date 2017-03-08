package io.behindthemath.mjolnir.source.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Loads keystores
 * 
 * @author Antony Lees
 */
class KeystoreLoader {
    private File file;

    KeystoreLoader(String keystoreFilePath) {
        file = new File(keystoreFilePath);
    }

    /**
     * Loads a keystore with the given name and password
     * @param keystorePassword the password attempt
     * @return the keystore or null if the keystore cannot be opened
     */
    KeyStore loadKeystore(String keystorePassword) {

        FileInputStream is = null;
        try {
            // Load the keystore in the user's home directory
            is = new FileInputStream(file);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(is, keystorePassword.toCharArray());
            return keystore;
        } catch (java.security.cert.CertificateException e) {
            throw new KeystoreException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new KeystoreException(e);
        } catch (FileNotFoundException e) {
            // Keystore does not exist
            throw new KeystoreException(e);
        } catch (KeyStoreException e) {
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

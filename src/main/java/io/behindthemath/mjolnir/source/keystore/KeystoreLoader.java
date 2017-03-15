package io.behindthemath.mjolnir.source.keystore;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Loads keystores.
 * 
 * @author Antony Lees
 */
class KeystoreLoader {
    private BufferedInputStream bufferedInputStream;
    private KeyStore keystore;

    KeystoreLoader(String keystoreFilePath) throws FileNotFoundException {
        File file = new File(keystoreFilePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        try {
            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            throw new KeystoreException(e);
        }
    }

    /**
     * Loads the keystore with the given password.
     *
     * @param keystorePassword the password attempt
     * @return The keystore, or null if the keystore cannot be opened.
     */
    KeyStore loadKeystore(char[] keystorePassword) {
        try {
            // Load the keystore in the user's home directory
            bufferedInputStream.reset();
            keystore.load(bufferedInputStream, keystorePassword);
            return keystore;
            // TODO: EOFException might mean we're skipping guesses
        } catch (CertificateException | NoSuchAlgorithmException | FileNotFoundException e) {
            throw new KeystoreException(e);
        } catch (IOException e) {
            if ((e.getCause() instanceof UnrecoverableKeyException) &&
                    (e.getCause().getMessage().contains("Password verification failed"))) return null;

            throw new KeystoreException(e);
        }
    }
}

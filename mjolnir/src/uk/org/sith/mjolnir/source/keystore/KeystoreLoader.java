package uk.org.sith.mjolnir.source.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class KeystoreLoader {

	public KeyStore loadKeystore(String keystoreName, String keystorePassword) {

		FileInputStream is = null;
		try {
			// Load the keystore in the user's home directory
			File file = new File(keystoreName);
			is = new FileInputStream(file);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, keystorePassword.toCharArray());
			return keystore;
		} catch (java.security.cert.CertificateException e) {
			e.printStackTrace();
			throw new KeystoreException();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new KeystoreException();
		} catch (FileNotFoundException e) {
			// Keystore does not exist
			e.printStackTrace();
			throw new KeystoreException();
		} catch (KeyStoreException e) {
			e.printStackTrace();
			throw new KeystoreException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KeystoreException();
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

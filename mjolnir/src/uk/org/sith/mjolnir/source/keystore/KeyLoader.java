package uk.org.sith.mjolnir.source.keystore;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class KeyLoader {

	public Key loadKey(KeyStore keystore, String keyAlias, String password) {

		try {
			// get my private key
			Key key = keystore.getKey(keyAlias, password.toCharArray());
			return key;
		} catch (NoSuchAlgorithmException e) {
			// let it return null
		} catch (KeyStoreException e) {
			// let it return null
		} catch (UnrecoverableKeyException e) {
			// let it return null
		}
		return null;

	}

}

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

	private KeyLoader keyLoader;
	private KeyStore keyStore;
	
	private String keystorePassword;
	private String keystoreName;
	private String keyName;

	@Override
	public void setup() {
		KeystoreLoader keystoreLoader = new KeystoreLoader();
		keyStore = keystoreLoader.loadKeystore(keystoreName, keystorePassword);
		keyLoader = new KeyLoader();
	}

	@Override
	public boolean attempt(String attempt) {
		Key key = keyLoader.loadKey(keyStore, keyName, attempt);
		// return true if the key is not null (ie null key == not found)
		return key != null;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	public void setKeystoreName(String keystoreName) {
		this.keystoreName = keystoreName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
}

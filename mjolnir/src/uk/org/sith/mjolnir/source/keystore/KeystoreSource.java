package uk.org.sith.mjolnir.source.keystore;

import java.security.KeyStore;

import uk.org.sith.mjolnir.source.Source;

/**
 * Source of key attacks.  Keystore must be loadable
 * 
 * @author Antony Lees
 */
public class KeystoreSource implements Source {
	
	private KeystoreLoader keystoreLoader;
	
	private String keystoreName;

	/*
	 * (non-Javadoc)
	 * @see org.sith.mjolnir.source.Source#setup()
	 */
	@Override
	public void setup() {
		keystoreLoader = new KeystoreLoader();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sith.mjolnir.source.Source#attempt(java.lang.String)
	 */
	@Override
	public boolean attempt(String attempt) {
		KeyStore keystore = keystoreLoader.loadKeystore(keystoreName, attempt);
		// return true if the key is not null (ie null key == not found)
		return keystore != null;
	}
	
	public void setKeystoreName(String keystoreName) {
		this.keystoreName = keystoreName;
	}

}

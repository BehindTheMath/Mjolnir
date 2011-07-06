package uk.org.sith.mjolnir.source.keystore;

import java.security.KeyStore;

import uk.org.sith.mjolnir.source.Source;

public class KeystoreSource implements Source {

	private String keystoreName;

	@Override
	public void load() {

		KeystoreLoader loader = new KeystoreLoader();
		KeyStore keystore = loader.loadKeystore("my.keystore", "password");

	}

	@Override
	public boolean crack() {
		// TODO Auto-generated method stub
		return false;
	}

}

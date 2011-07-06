package uk.org.sith.mjolnir.source.keystore;

import java.security.KeyStore;

import uk.org.sith.mjolnir.source.Source;

public class KeySource implements Source {

	// TODO inject these
	private String keystoreName = "my.keystore";

	private String keystorePassword = "password";

	@Override
	public void load() {

		KeystoreLoader loader = new KeystoreLoader();
		KeyStore keystore = loader.loadKeystore(keystoreName, keystorePassword);

	}

	@Override
	public boolean crack() {
		// TODO Auto-generated method stub
		return false;
	}

}

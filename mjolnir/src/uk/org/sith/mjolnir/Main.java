package uk.org.sith.mjolnir;

import java.security.Key;
import java.security.KeyStore;

public class Main {

	private static final int PASSWORD_MAX_LENGTH = 4;
	private static final int PASSWORD_MIN_LENGTH = 2;

	public static void main(String[] args) {

		KeystoreLoader loader = new KeystoreLoader();
		KeyStore keystore = loader.loadKeystore("my.keystore", "password");

		PasswordGenerator generator = new DictionaryPasswordGenerator("fsm");
		KeyLoader keyLoader = new KeyLoader();

		String password = generator.generate();
		while (password != null && !password.equals("")) {
			// get my private key
			System.out.println(password);
			Key key = keyLoader.loadKey(keystore, "weblore", password);
			if (key != null) {
				System.out.println("FOUND! Password is " + password);
				System.exit(1);
			}
			password = generator.generate();
		}

	}
}

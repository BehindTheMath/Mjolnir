package uk.org.sith.mjolnir.source.keystore;

/**
 * General exception class for keystores
 * 
 * @author Antony Lees
 */
public class KeystoreException extends RuntimeException {

	private static final long serialVersionUID = 7837507479354995645L;
	
	public KeystoreException(Throwable cause) {
		super(cause);
	}

}
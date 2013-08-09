package uk.org.sith.mjolnir.source;

/**
 * Defines the source to be cracked eg a keystore
 * 
 * @author Antony Lees
 */
public interface Source {
	
	/**
	 * Called to setup the source before any attempts are made
	 */
	public void setup();
	
	/**
	 * Attempt to crack the password.  Return true if it worked
	 * @return true if the attempt succeeded
	 */
	public boolean attempt(String attempt);

}

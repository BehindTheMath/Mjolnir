package io.behindthemath.mjolnir.attack;

/**
 * Heuristic version of the brute force attack, designed to use known strings
 * with additional random values. For example, if the user knows they probably
 * used 'password123' but can't remember the exact numbers
 * 
 * @author Antony Lees
 */
public class HeuristicBruteForce extends BruteForce {

	private String word;

	public HeuristicBruteForce(String word, char[] characterSet, int guessLength) {
		super(characterSet, guessLength);
		this.word = word;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sith.mjolnir.attack.Attack#getNextAttempt()
	 */
	@Override
	public String getNextAttempt() {
		String chars = super.getNextAttempt();
		return word + chars;
	}

}

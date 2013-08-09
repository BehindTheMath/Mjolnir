package uk.org.sith.mjolnir.run;

import uk.org.sith.mjolnir.attack.Attack;
import uk.org.sith.mjolnir.source.Source;

/**
 * Threaded classes that actually runs the attack attempts. Threads communicate with each other using a {@link BooleanLock}
 * 
 * @author Antony Lees
 */
public class AttackWorker implements Runnable {
	
	private Attack attack;
	private Source source;
	private BooleanLock lock;
	private int reportEvery;
	
	public AttackWorker(Attack attack, Source source, BooleanLock lock) {
		this.attack = attack;
		this.source = source;
		this.lock = lock;
		reportEvery = 10000;
	}

	/**
	 * Runs the actual attack.  Reporting can be changed so that each attempt isn't logged
	 */
	@Override
	public void run() {
		int attemptNumber = 0;
		String attempt = null;
		while (lock.isFalse()) {
			attemptNumber++;
			attempt = attack.getNextAttempt();
			boolean found = source.attempt(attempt);
			if (found) {
				System.out.println("Password Found: " + attempt);
				lock.setValue(true);
			}

			if (attemptNumber % reportEvery == 0) {
				System.out.println("Tried: " + attempt);
			}
		}
	}

}

package io.behindthemath.mjolnir.run;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.source.Source;

/**
 * Runs the attack for the given attack against the given source, for the given
 * number of threads
 * 
 * @author Antony Lees
 */
public class AttackRunner {

	private int numberOfWorkers;
	private Attack attack;
	private Source source;

	public AttackRunner(Attack attack, Source source, int numberOfWorkers) {
		this.attack = attack;
		this.numberOfWorkers = numberOfWorkers;
		this.source = source;
	}

	/**
	 * Starts the attack. Threads communicate with each other using the
	 * {@link BooleanLock} which stops all the threads if any thread finds the
	 * answer
	 */
	public void start() {

		BooleanLock lock = new BooleanLock();

		for (int i = 0; i < numberOfWorkers; i++) {
			AttackWorker attackWorker = new AttackWorker(attack, source, lock);
			Thread attackThread = new Thread(attackWorker);
			attackThread.start();
		}

	}

}

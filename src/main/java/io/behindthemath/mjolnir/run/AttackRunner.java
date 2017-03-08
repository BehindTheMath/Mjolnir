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
    private Attack attack;
    private Source source;
    private int numberOfWorkers;
    private int reportEvery;

    public AttackRunner(Attack attack, Source source, int numberOfWorkers, int reportEvery) {
        this.attack = attack;
        this.source = source;
        this.numberOfWorkers = numberOfWorkers;
        this.reportEvery = reportEvery;
    }

    /**
     * Starts the attack. Threads communicate with each other using the
     * {@link BooleanLock} which stops all the threads if any thread finds the
     * answer
     */
    public void start() {
        BooleanLock lock = new BooleanLock();

        for (int i = 0; i < numberOfWorkers; i++) {
            AttackWorker attackWorker = new AttackWorker(attack, source, lock, reportEvery);
            Thread attackThread = new Thread(attackWorker);
            attackThread.start();
        }
    }
}

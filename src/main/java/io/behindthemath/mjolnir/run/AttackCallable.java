package io.behindthemath.mjolnir.run;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.source.Source;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by Behind The Math on 3/1/2017.
 */
public class AttackCallable implements Callable<String> {
    private final int threadNumber;
    private final int reportEvery;
    private final Attack attack;
    private final Source source;

    public AttackCallable(final int threadNumber, final int reportEvery, final Attack attack, final Source source) {
        this.threadNumber = threadNumber;
        this.reportEvery = reportEvery;
        this.attack = attack;
        this.source = source;
    }

    @Override
    public synchronized String call() throws Exception {
        int attemptNumber = 0;
        char[] attempt;

        while(!Thread.interrupted()) {
            attemptNumber++;
            attempt = attack.getNextAttempt();
            boolean found = source.attempt(attempt);
            if (found) {
                return String.valueOf(attempt);
            }

            if (reportEvery != 0 && attemptNumber % reportEvery == 0) {
                attemptNumber = 0;
                System.out.println("Thread # " + threadNumber + ": Attempt: " + String.valueOf(attempt));
            }
        }

        return null;
    }
}

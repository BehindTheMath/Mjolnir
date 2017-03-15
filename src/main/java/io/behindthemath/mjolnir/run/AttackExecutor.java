package io.behindthemath.mjolnir.run;

import io.behindthemath.mjolnir.attack.Attack;
import io.behindthemath.mjolnir.source.Source;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Behind The Math on 3/1/2017.
 */
public class AttackExecutor {
    private final int numberOfWorkers;
    private final Attack attack;
    private final Source source;
    private final int reportEvery;

    public AttackExecutor(Attack attack, Source source, int numberOfWorkers, int reportEvery) {
        this.attack = attack;
        this.numberOfWorkers = numberOfWorkers;
        this.source = source;
        this.reportEvery = reportEvery;
    }

    public String start() {
        final ExecutorService pool = Executors.newFixedThreadPool(numberOfWorkers);
        final List<AttackCallable> attackCallablesList = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            attackCallablesList.add(new AttackCallable(i, reportEvery, attack, source));
        }

        try {
            return pool.invokeAny(attackCallablesList);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            pool.shutdownNow();
        }
        return null;
    }
}

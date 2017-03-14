package io.behindthemath.mjolnir.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Behind The Math on 3/13/2017.
 */
public class Stopwatch {
    private long startTime = 0;
    private long elapsedSoFar = 0;

    public Stopwatch start() {
        startTime = new Date().getTime();
        return this;
    }

    public Stopwatch stop() {
        elapsedSoFar += new Date().getTime() - startTime;
        startTime = 0;
        return this;
    }

    public Stopwatch clear() {
        startTime = 0;
        elapsedSoFar = 0;
        return this;
    }

    public long getTime() {
        processElapsedTime();
        return elapsedSoFar;
    }

    public long getTime(TimeUnit timeUnit) {
        processElapsedTime();
        return timeUnit.convert(elapsedSoFar, TimeUnit.MILLISECONDS);
    }

    public Stopwatch printTime() {
        processElapsedTime();
        System.out.println("Time elapsed: " + elapsedSoFar + " milliseconds.");
        return this;
    }

    public Stopwatch printTime(TimeUnit timeUnit) {
        processElapsedTime();
        System.out.println("Time elapsed: " + timeUnit.convert(elapsedSoFar, TimeUnit.MILLISECONDS) + " " + timeUnit.toString());
        return this;
    }

    private void processElapsedTime() {
        if (startTime != 0) {
            elapsedSoFar += ((new Date()).getTime() - startTime);
            startTime = new Date().getTime();
        }
    }
}

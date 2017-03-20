package io.behindthemath.mjolnir.utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Behind The Math on 3/14/2017.
 */
public class StopwatchTest {
    /**
     * Tests {@link Stopwatch#getTime()}. Since it takes a few ms for processing, the method will assert true as long
     * as it's within 5ms.
     *
     * @throws Exception
     */
    @Test
    public void getTimeTest() throws Exception {
        final long expectedDurationMilliseconds = 3000;
        Stopwatch stopwatch = new Stopwatch();

        stopwatch.start();
        Thread.sleep(expectedDurationMilliseconds);
        stopwatch.stop();
        long elapsed = stopwatch.getTime();

        System.out.println("Expected Duration: " + expectedDurationMilliseconds);
        System.out.println("Actual Duration: " + elapsed);
        assertTrue(elapsed >= expectedDurationMilliseconds - 5 && elapsed <= expectedDurationMilliseconds + 5);
    }

    @Test
    public void getTimeTest_inSeconds() throws Exception {
        final long expectedDurationSeconds = 3;
        Stopwatch stopwatch = new Stopwatch();

        stopwatch.start();
        Thread.sleep(expectedDurationSeconds * 1000);
        stopwatch.stop();
        long elapsed = stopwatch.getTime(TimeUnit.SECONDS);

        System.out.println("Expected Duration: " + expectedDurationSeconds);
        System.out.println("Actual Duration: " + elapsed);
        assertEquals(expectedDurationSeconds, elapsed);
    }
}
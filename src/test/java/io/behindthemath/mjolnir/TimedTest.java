package io.behindthemath.mjolnir;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Behind The Math on 3/4/2017.
 */
public class TimedTest {
    private Date startDate;

    public void markStart() {
        startDate = new Date();
    }

    public void markEnd() {
        Date endDate = new Date();
        System.out.println("Time to run: " + TimeUnit.SECONDS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS) + " seconds.");
    }
}

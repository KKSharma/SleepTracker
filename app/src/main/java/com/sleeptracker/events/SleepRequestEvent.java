package com.sleeptracker.events;

/**
 * Created by synerzip on 02/10/16.
 */

public class SleepRequestEvent extends FitbitRequestEvent<SleepResponseEvent> {
    private final String mDate;

    public SleepRequestEvent(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }
}

package com.sleeptracker.events;

public class SleepRequestEvent extends FitbitRequestEvent<SleepResponseEvent> {
    private final String mDate;

    public SleepRequestEvent(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }
}

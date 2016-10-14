package com.sleeptracker.events;

public class ProgressBarEvent {
    private final boolean show;

    public ProgressBarEvent(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }
}
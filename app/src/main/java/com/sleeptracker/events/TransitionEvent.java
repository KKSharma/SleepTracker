package com.sleeptracker.events;

import android.os.Bundle;

public class TransitionEvent {
    protected String name;
    protected Bundle data;

    public TransitionEvent(String name, Bundle data) {
        this.name = name;
        this.data = data;
    }

    public TransitionEvent(String name) {
        this(name, null);
    }

    public String getName() {
        return name;
    }

    public Bundle getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Bundle: %s", name, data == null ? "null" : data.toString());
    }
}

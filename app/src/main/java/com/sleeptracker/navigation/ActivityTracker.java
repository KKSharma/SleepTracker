package com.sleeptracker.navigation;

import android.app.Activity;

import com.sleeptracker.events.TransitionEvent;

public interface ActivityTracker {
    void recordTransition(Activity source, TransitionEvent event, Class<?> target);
}
package com.sleeptracker.navigation;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sleeptracker.events.TransitionEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;


public class AppNavigationManager implements Application.ActivityLifecycleCallbacks {

    private final Map<Class<?>, Map<String, Transition>> activityTable = new HashMap<>();
    private final Map<Class<?>, Action> exitActionTable = new HashMap<>();

    private Activity currentActivity;
    private ActivityTracker tracker = new ActivityLogger();

    public AppNavigationManager(Context context) {
        final Application app = (Application) context.getApplicationContext();
        app.registerActivityLifecycleCallbacks(this);
        EventBus.getDefault().register(this);
    }

    // So we can change the implementation of the transition table in one place
    private static Map<String, Transition> newTransitionTable() {
        return new HashMap<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        currentActivity = activity;

    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;

    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (currentActivity == activity) {
            currentActivity = null;
        }
    }

    private Transition lookup(TransitionEvent event) {
        if ((currentActivity != null)
                && activityTable.containsKey(currentActivity.getClass())
                && activityTable.get(currentActivity.getClass()).containsKey(event.getName())) {
            return activityTable.get(currentActivity.getClass()).get(event.getName());
        } else {
            return null;
        }
    }

    private Action lookupExitAction(Class<?> from) {
        if (currentActivity != null
                && exitActionTable.containsKey(from)) {
            return exitActionTable.get(from);
        } else return null;
    }

    public void onEventMainThread(TransitionEvent event) {
        final Transition t = lookup(event);
        if (t != null) {
            final Intent intent = new Intent(currentActivity, t.target);
            if (event.getData() != null) {
                intent.putExtras(event.getData());
            }

            Action exitAction = lookupExitAction(currentActivity.getClass());
            if (exitAction != null) exitAction.execute(currentActivity, intent);

            if (t.action != null) {
                t.action.execute(currentActivity, intent);
            }
            tracker.recordTransition(currentActivity, event, t.target);
            currentActivity.startActivity(intent);
        }
    }


    public void addTransition(Class<?> from, String event, Transition transition) {
        if (!activityTable.containsKey(from)) {
            activityTable.put(from, newTransitionTable());
        }
        final Map<String, Transition> transitionTable = activityTable.get(from);
        transitionTable.put(event, transition);
    }

    public void addExitAction(Class<?> from, Action action) {
        exitActionTable.put(from, action);
    }

    // Graph base methods
    public Collection<Transition> edges(Class<?> activityClass) {
        return activityTable.containsKey(activityClass) ? activityTable.get(activityClass).values() : null;
    }

    public Set<Class<?>> getNeighbors(Class<?> activityClass) {
        final Set<Class<?>> activities = new HashSet<>();
        for (Transition t : edges(activityClass)) {
            activities.add(t.target);
        }
        return activities;
    }

    public Set<Class<?>> nodes() {
        return activityTable.keySet();
    }

    public interface Action {
        void execute(Activity current, Intent intent);

    }

    public static class Transition {
        private final Class<?> target;
        private final Action action;

        public Transition(Class<?> target, Action action) {
            this.target = target;
            this.action = action;
        }

        @Override
        public String toString() {
            return String.format("Target: %s", target.getSimpleName());
        }
    }

    public static class ActivityLogger implements ActivityTracker {

        @Override
        public void recordTransition(Activity source, TransitionEvent event, Class<?> target) {
            Log.d("O-->O", String.format("%s--%s-->%s", source.getClass().getSimpleName(), event.getName(), target.getSimpleName()));
        }
    }
}
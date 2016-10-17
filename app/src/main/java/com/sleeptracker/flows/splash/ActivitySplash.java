package com.sleeptracker.flows.splash;

import android.os.Bundle;

import com.sleeptracker.appbase.ActivityFitbit;
import com.sleeptracker.appbase.FitbitApplication;
import com.sleeptracker.events.TransitionEvent;

import sleeptracker.com.libfitbit.core.FitbitClient;

public class ActivitySplash extends ActivityFitbit {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FitbitClient.getInstance().getAuthToken() != null) {
            mEventBus.post(new TransitionEvent(FitbitApplication.SCREEN_DASHBOARD));
            return;
        }
        mEventBus.post(new TransitionEvent(FitbitApplication.SCREEN_LOGIN));
    }
}

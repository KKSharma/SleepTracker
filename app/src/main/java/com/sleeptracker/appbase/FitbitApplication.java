package com.sleeptracker.appbase;

import android.support.multidex.MultiDexApplication;

import com.sleeptracker.FitbitOauth2;
import com.sleeptracker.flows.login.ActivityLogin;
import com.sleeptracker.navigation.AppNavigationManager;
import com.sleeptracker.navigation.AppNavigationManager.Transition;


public class FitbitApplication extends MultiDexApplication {

    public static final String OAUTH2 = "Authorization";

    @Override
    public void onCreate() {
        super.onCreate();
        initNavigationFlow();
    }

    private void initNavigationFlow() {
        AppNavigationManager appNavigationManager = new AppNavigationManager(this);
        Transition oauthTransition = new Transition(FitbitOauth2.class, null);
        appNavigationManager.addTransition(ActivityLogin.class, OAUTH2, oauthTransition);
    }
}


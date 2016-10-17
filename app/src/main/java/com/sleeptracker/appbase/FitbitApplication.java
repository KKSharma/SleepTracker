package com.sleeptracker.appbase;

import android.support.multidex.MultiDexApplication;

import com.sleeptracker.BuildConfig;
import com.sleeptracker.FitbitOauth2;
import com.sleeptracker.flows.dashboard.ActivityDashboard;
import com.sleeptracker.flows.login.ActivityLogin;
import com.sleeptracker.flows.splash.ActivitySplash;
import com.sleeptracker.navigation.AppNavigationManager;
import com.sleeptracker.navigation.AppNavigationManager.Transition;

import sleeptracker.com.libfitbit.core.FitbitClient;


public class FitbitApplication extends MultiDexApplication {

    public static final String SCREEN_LOGIN = "SCREEN_LOGIN_ACTIVITY";
    public static final String OAUTH2 = "SCREEN_AUTHORIZATION";
    public static final String SCREEN_DASHBOARD = "SCREEN_DASHBOARD_ACTIVITY";

    @Override
    public void onCreate() {
        super.onCreate();
        FitbitClient.init(this, BuildConfig.BUILD_TYPE);
        initNavigationFlow();
    }

    private void initNavigationFlow() {
        AppNavigationManager appNavigationManager = new AppNavigationManager(this);
        Transition oauthTransition = new Transition(FitbitOauth2.class, null);
        Transition dashboardTransition = new Transition(ActivityDashboard.class, null);
        Transition loginTransition = new Transition(ActivityLogin.class, null);

        appNavigationManager.addTransition(ActivitySplash.class, SCREEN_LOGIN, loginTransition);
        appNavigationManager.addTransition(ActivitySplash.class, SCREEN_DASHBOARD, dashboardTransition);

        appNavigationManager.addTransition(FitbitOauth2.class, SCREEN_DASHBOARD, dashboardTransition);

        appNavigationManager.addTransition(ActivityLogin.class, OAUTH2, oauthTransition);
        appNavigationManager.addTransition(ActivityLogin.class, SCREEN_DASHBOARD, dashboardTransition);

    }
}


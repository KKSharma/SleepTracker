package com.sleeptracker.flows.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sleeptracker.R;
import com.sleeptracker.appbase.ActivityFitbit;
import com.sleeptracker.widgets.NavigationDrawer;

import butterknife.Bind;

public class ActivityDashboard extends ActivityFitbit {
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    @Bind(R.id.app_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.navigation_view)
    NavigationDrawer mNavigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView(R.layout.activity_dashboard);
        setSupportActionBar(mToolbar);
        setUpNavigationDrawer();
    }

    private void setUpNavigationDrawer() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mDrawerLayout.openDrawer(mNavigationView);
            }
        });
        //mNavigationView.setNavigationItemSelectedListener(this);
    }

}

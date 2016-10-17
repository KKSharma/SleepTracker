package com.sleeptracker.appbase;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.sleeptracker.R;
import com.sleeptracker.events.ProgressBarEvent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ActivityFitbit extends AppCompatActivity {
    protected EventBus mEventBus;
    protected ProgressBar mProgressBar;
    protected CoordinatorLayout mCoordinatorLayoutView;

    protected void initializeView(@LayoutRes int layoutResID) {
        setContentView(layoutResID);
        ButterKnife.bind(this);
        setUpToolbar();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = EventBus.getDefault();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEventBus.unregister(this);
    }

    public void onEventMainThread(ProgressBarEvent progressBarEvent) {
        handleProgressBarVisibility(progressBarEvent.isShow());
    }

    protected void handleProgressBarVisibility(boolean isVisible) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    protected void setUpToolbar() {
        mCoordinatorLayoutView = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }
}

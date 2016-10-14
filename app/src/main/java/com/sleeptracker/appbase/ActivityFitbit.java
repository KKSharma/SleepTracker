package com.sleeptracker.appbase;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sleeptracker.events.ProgressBarEvent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ActivityFitbit extends AppCompatActivity {
    protected EventBus mEventBus;

    protected void initializeView(@LayoutRes int layoutResID) {
        setContentView(layoutResID);
        ButterKnife.bind(this);
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
    }
}

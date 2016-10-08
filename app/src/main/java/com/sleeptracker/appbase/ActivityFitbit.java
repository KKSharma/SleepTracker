package com.sleeptracker.appbase;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by synerzip on 07/10/16.
 */

public class ActivityFitbit extends AppCompatActivity {

    protected void initializeView(@LayoutRes int layoutResID) {
        setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}

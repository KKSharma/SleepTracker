package com.sleeptracker.flows.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.sleeptracker.FitbitOauth2;
import com.sleeptracker.R;
import com.sleeptracker.api.Api;
import com.sleeptracker.appbase.ActivityFitbit;
import com.sleeptracker.events.SleepRequestEvent;
import com.sleeptracker.events.SleepResponseEvent;

import butterknife.Bind;
import butterknife.OnClick;
import sleeptracker.com.libfitbit.model.SleepPayload;

public class ActivityLogin extends ActivityFitbit {
    private static final String TAG = ActivityLogin.class.getSimpleName();
    private static final int REQUEST_CODE_LOGIN = 111;
    public static final int RESULT_CODE_LOGIN = 112;
    @Bind(R.id.login_button)
    Button mLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView(R.layout.activity_login);
        mLoginButton.setText("Login");
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        Intent intent = new Intent(this, FitbitOauth2.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(@NonNull SleepResponseEvent sleepResponseEvent) {
        if (sleepResponseEvent.isSuccess()) {
            SleepPayload payload = sleepResponseEvent.getSleepResult();
            Log.d(TAG, payload.getSummary().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_CODE_LOGIN) {
            Api.getSleepForDate(new SleepRequestEvent("2016-09-30"));
        }
    }
}

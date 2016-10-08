package com.sleeptracker.flows.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sleeptracker.FitbitOauth2;
import com.sleeptracker.R;
import com.sleeptracker.appbase.ActivityFitbit;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by synerzip on 07/10/16.
 */

public class ActivityLogin extends ActivityFitbit {
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
        startActivity(new Intent(this, FitbitOauth2.class));

    }
}

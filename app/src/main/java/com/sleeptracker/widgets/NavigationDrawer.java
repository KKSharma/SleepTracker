package com.sleeptracker.widgets;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleeptracker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NavigationDrawer extends NavigationView {

    private static final String TAG = NavigationDrawer.class.getSimpleName();
    @Bind(R.id.email)
    ImageView mUserImage;
    @Bind(R.id.profile_image)
    TextView mProfileName;

    public NavigationDrawer(Context context) {
        super(context);
        setUp();
    }

    public NavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public NavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        inflateHeaderView(R.layout.navigation_drawer_header);
        View headerView = getHeaderView(0);
        ButterKnife.bind(headerView);
    }

    public void updateUserName(String handle) {
        mProfileName.setText(handle);
    }

}

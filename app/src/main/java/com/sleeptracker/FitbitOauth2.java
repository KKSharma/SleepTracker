
package com.sleeptracker;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sleeptracker.api.Api;
import com.sleeptracker.appbase.ActivityFitbit;
import com.sleeptracker.events.SleepRequestEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import butterknife.Bind;
import sleeptracker.com.libfitbit.core.FitbitClient;

public class FitbitOauth2 extends ActivityFitbit {
    private static final String TAG = FitbitOauth2.class.getSimpleName();
    private static String CLIENT_ID = "2282HR";
    private static String CLIENT_SECRET = "f1777e08eb6fabe4392c39562593be63";
    private static String REDIRECT_URI = "https://localhost/oauth2callback";
    private static String TOKEN_URL = "https://api.fitbit.com/oauth2/token";
    private static String OAUTH_URL = "https://www.fitbit.com/oauth2/authorize";
    private static String OAUTH_SCOPE = "activity+heartrate+location+nutrition+settings+sleep+profile+social+weight";
    private static String GRANT_TYPE = "authorization_code";

    @Bind(R.id.webview)
    WebView mWebView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView(R.layout.oauth);
        mWebView.getSettings().setJavaScriptEnabled(true);
        String lang = Locale.getDefault().getISO3Language();
        String country = lang;

        String url = OAUTH_URL + "?redirect_uri=" + REDIRECT_URI
                + "&approval_prompt=force&access_type=offline" + "&lang=" + lang + "&cntry=" + country + "&response_type=code&client_id=" + CLIENT_ID + "&scope=" + OAUTH_SCOPE;
        System.out.println("url : " + url);
        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {

            Intent resultIntent = new Intent();

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("?code=")) {
                    Uri uri = Uri.parse(url);
                    authCode = uri.getQueryParameter("code");
                    Log.i("", "CODE : " + authCode);
                    resultIntent.putExtra("code", authCode);
                    FitbitOauth2.this.setResult(Activity.RESULT_OK, resultIntent);
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    new TokenGet(authCode).execute();
                    return true;
                } else if (url.contains("error=access_denied")) {
                    Log.i("", "ACCESS_DENIED_HERE");
                    resultIntent.putExtra("code", authCode);
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT)
                            .show();

                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            String authCode;

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private class TokenGet extends AsyncTask<String, String, JSONObject> {

        private String mCode;

        public TokenGet(String code) {
            mCode = code;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            GetAccessToken jParser = new GetAccessToken();
            JSONObject json = jParser.gettoken(TOKEN_URL, mCode, CLIENT_ID, CLIENT_SECRET,
                    REDIRECT_URI, GRANT_TYPE);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (json != null) {
                try {
                    String token = json.getString("access_token");
                    String tokenExpires = json.getString("expires_in");

                    Log.d("Token Access", token);
                    Log.d("Expire", tokenExpires);
                    setAuthToken(token);
                    Toast.makeText(FitbitOauth2.this,
                            "access_token:\n " + token + "\n\nexpires: " + tokenExpires, Toast.LENGTH_LONG)
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    mProgressBar.setVisibility(View.GONE);
                }

            } else {
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }
            FitbitOauth2.this.finish();
        }
    }

    private synchronized void setAuthToken(String authToken) {
        try {
            FitbitClient.init(this, BuildConfig.BUILD_TYPE);
            FitbitClient.getInstance().setAuthToken(authToken);
            Api.getSleepForDate(new SleepRequestEvent("2016-09-30"));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}


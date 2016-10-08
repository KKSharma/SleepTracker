package com.sleeptracker.events;


import com.google.common.reflect.TypeToken;
import com.sleeptracker.BuildConfig;

import retrofit2.Call;
import retrofit2.Response;

public abstract class FitbitRequestEvent<ResponseEvent extends FitbitResponseEvent> {
    private int retries = 1;
    private String savedAuthToken = null;
    final TypeToken<ResponseEvent> type = new TypeToken<ResponseEvent>(getClass()) {
    };

    protected FitbitRequestEvent() {
        // make sure subclass defines a response event
        if (BuildConfig.DEBUG && type.getType().toString().equals("ResponseEvent"))
            throw new AssertionError("No FitbitResponseEvent defined");
    }

    protected FitbitRequestEvent(int retries) {
        // make sure subclass defines a response event
        if (BuildConfig.DEBUG && type.getType().toString().equals("ResponseEvent"))
            throw new AssertionError("No FitbitResponseEvent defined");
        this.retries = retries;
    }

    public boolean canRetry() {
        return retries > 0;
    }

    public FitbitRequestEvent retry() {
        retries -= 1;
        return this;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    @Override
    public String toString() {
        return String.format("%s{retries=%d, needs auth=%b}", this.getClass().getSimpleName(), retries, this.needsAuth());
    }

    public boolean needsAuth() { // everything needs authorization by default. Override in subclass
        return true;            // to define a request that doesn't need an auth token.
    }

    public FitbitResponseEvent createResponse(Call<?> call, Response<?> response, FitbitRequestEvent request) {
        FitbitResponseEvent responseEvent = null;

        try {
            responseEvent = ((ResponseEvent) type.getRawType().newInstance()).init(call, response, request);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return responseEvent;
    }

    public FitbitResponseEvent createResponse(Throwable t, FitbitRequestEvent request) {
        try {
            return ((ResponseEvent) type.getRawType().newInstance()).init(t, request);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSavedAuthToken() {
        return savedAuthToken;
    }

    public void saveAuthToken() {
        this.savedAuthToken = this.getAuthToken();
    }

    // subclasses that need to send a specific auth token like ChangePassword and Register
    // can override this method
    public String getAuthToken() {
        return null;
    }
}

package com.sleeptracker.events;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import sleeptracker.com.libfitbit.model.FitbitError;
import sleeptracker.com.libfitbit.model.response.FitbitResult;

public abstract class FitbitResponseEvent<RESULT extends FitbitResult> {
    public static final int ERROR_NUMBER_YOUR_PROFILE_IS_HIDDEN_MUST_BE_VISIBLE_FOR_DAILY_MATCHES = 1001;
    public static final int FORCE_UPGRADE_RESPONSE_NUMBER = 99999;
    public static final int PROBLEM_WITH_USER_INPUT = 1000;
    public static final int AUTHENTICATION_KEY_EXPIRED_CODE = 7777;
    public static final int AUTHENTICATION_KEY_FAILED_CODE = 1005;
    public static final String AUTHENTICATION_FAILED = "authentication failed";


    private  Call<RESULT> call;
    private  Response<RESULT> response;
    private  Throwable t;
    private  FitbitRequestEvent request;

    public FitbitResponseEvent(Call<RESULT> call, Response<RESULT> response, FitbitRequestEvent<? extends FitbitResponseEvent<RESULT>> request) {

        this.call = call;
        this.response = response;
        this.request = request;
        t = null;
    }

    public FitbitResponseEvent(Throwable t, FitbitRequestEvent request) {
        this.t = t;
        this.request = request;
        call = null;
        response = null;
    }

    public FitbitResponseEvent() {
    }

    public FitbitRequestEvent getRequest() {
        return request;
    }

    public Response<?> getResponse() {
        return response;
    }


    public FitbitResult getResult() {
        if (getResponse() != null){
            return (FitbitResult) getResponse().body();
        } else {
            return null;
        }
    }

    // returns true is http status is in 2xx range,
    public boolean isSuccess() {
        return getResponse() != null && getResponse().isSuccessful();
    }

    public FitbitError getServerError(){
        if (hasServerError()) {
            return getResult().getError();
        } else {
            return null;
        }
    }

    public boolean hasNoErrors(){
       return  isSuccess() && !hasServerError();
    }

    @Override
    public String toString() {
        if (isSuccess()) {
            return String.format("%s, %s\n", request, getResponse());
        } else {
            return String.format("%s, %s, %s\n", request, getResponse(), getErrorMessage());
        }
    }

    public Call<?> getCall() {
        return call;
    }

    public FitbitResponseEvent init(Call<RESULT> call, Response<RESULT> response, FitbitRequestEvent<?> request) {
        this.call = call;
        this.response = response;
        this.request = request;
        t = null;
        return this;
    }

    public FitbitResponseEvent init(Throwable t, FitbitRequestEvent request) {
        this.t = t;
        this.request = request;
        call = null;
        response = null;
        return this;
    }

    public FitbitError getError(){
        if (getResult() != null) {
            return getResult().getError();
        } else {
            return null;
        }
    }

    @NonNull
    public String getErrorMessage() {
        if (!isSuccess()) {
            if (t != null) {
                return t.getLocalizedMessage();
            } else {
                return getResponse().message();
            }
        } else if (hasServerError()){
            return getResult().getError().getMessage();
        }
        else {
            return "";
        }
    }

    public int getServerErrorNumber(){
        if (!hasServerError()){
            return 0;
        } else {
            return getServerError().getNumber();
        }
    }

    public String getServerErrorMessage(){
        if (hasServerError()){
            return getServerError().getMessage();
        } else {
            return "";
        }
    }
    // error predicate functions

    public boolean hasServerError() {
        return getResult() != null && getResult().getError() != null;
    }

    public  boolean isRetrofitError() {
        return !isSuccess();
    }

    public boolean isConnectException(){
        return this.t instanceof ConnectException;
    }

    public  boolean isLoginFailure() {
        return isSuccess()
                && hasServerError();
    }

    public  boolean isProfileHiddenError() {
        return hasServerError()
                && getResult().getError().getNumber() == ERROR_NUMBER_YOUR_PROFILE_IS_HIDDEN_MUST_BE_VISIBLE_FOR_DAILY_MATCHES;
    }
    public  boolean authKeyFailedOrExpired() {
        return (getResult() != null)
                && (getResult().getError() != null)
                && ((getResult().getError().getNumber() == AUTHENTICATION_KEY_EXPIRED_CODE)
                || (getResult().getError().getNumber() == AUTHENTICATION_KEY_FAILED_CODE));
    }

    public  boolean messageContainsAuthenticationFailed() {
        return  getResult()!=null
                && getResult().getError() != null
                && getResult().getError().getMessageEN() != null
                && getResult().getError().getMessageEN().contains(AUTHENTICATION_FAILED);
    }

    public  boolean isStatus(int code) {
        boolean retval= (getResponse() != null
                && getResponse().code() == code);
        return retval;
    }

    public int getStatus(){
        if (getResponse()==null) return 0;
        return getResponse().code();
    }

    public  boolean isAuthenticationError(){
        return isStatus(401)
                || authKeyFailedOrExpired()
                || messageContainsAuthenticationFailed();
    }

    public boolean shouldRetry(){
        if (getRequest().canRetry()) {
            if (this.hasServerError()) {
                Log.d("FitbitResponseEvent", String.format("server error number =%d", getServerErrorNumber()));
                if(getServerErrorNumber() == PROBLEM_WITH_USER_INPUT
                        || getServerErrorNumber() == ERROR_NUMBER_YOUR_PROFILE_IS_HIDDEN_MUST_BE_VISIBLE_FOR_DAILY_MATCHES
                        || getServerErrorNumber() == FORCE_UPGRADE_RESPONSE_NUMBER) {
                    Log.d("FitbitResponseEvent", "shouldRetry return false");
                    return false;
                } else {
                    Log.d("FitbitResponseEvent", "shouldRetry return true");
                    return true;
                }
            } else {
                Log.d("FitbitResponseEvent", "shouldRetry return true");
                return true;
            }
        } else {
            Log.d("FitbitResponseEvent", "shouldRetry return false");
            return false;
        }
    }


    private ResponseBody getErrorBody(){
        return getResponse().errorBody();
    }

    // okhttp3 reads the errorbody through a stream. When you call .string()
    // the first time, it reads it from the stream and then closes it. When you
    // call it the second time, it throws an error. So we need to call it once and
    // save the result to make the function idempotent.
    String errorBodyString=null;
    protected String getErrorBodyString(){
        if (errorBodyString == null) {
            try {
                errorBodyString = getErrorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return errorBodyString;
    }
}

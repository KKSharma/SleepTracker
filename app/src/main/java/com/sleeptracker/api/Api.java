package com.sleeptracker.api;

import android.util.Log;

import com.sleeptracker.events.FitbitRequestEvent;
import com.sleeptracker.events.SleepRequestEvent;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sleeptracker.com.libfitbit.core.FitbitClient;
import sleeptracker.com.libfitbit.model.SleepPayload;

public class Api {
    private static final String TAG = Api.class.getSimpleName();

    public static void getSleepForDate(SleepRequestEvent event) {
        Call<SleepPayload> call = FitbitClient.getInstance().getFitBitApi().getSleepForDate(event.getDate());
        call.enqueue(new FitbitCallback<SleepPayload>(event));
    }

    static class FitbitCallback<ResponseType> implements Callback<ResponseType> {
        private final FitbitRequestEvent request;
        public FitbitCallback(FitbitRequestEvent requestEvent) {
            this.request = requestEvent;
        }

        @Override
        public void onResponse(Call<ResponseType> call, Response<ResponseType> response) {
            Object fitbitResponse = request.createResponse(call, response, request);
            Log.w(TAG, "fitbitResponse = " + fitbitResponse + " : : " + response.raw().toString());
            if (fitbitResponse == null) {
                Log.w(TAG, " FitbitCallback onResponse: fitbitResponse == null");
            } else {
                EventBus.getDefault().post(fitbitResponse);
            }
        }

        @Override
        public void onFailure(Call<ResponseType> call, Throwable t) {
            EventBus.getDefault().post(request.createResponse(t, request));
        }
    }
}

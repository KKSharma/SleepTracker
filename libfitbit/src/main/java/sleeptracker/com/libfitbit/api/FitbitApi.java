package sleeptracker.com.libfitbit.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sleeptracker.com.libfitbit.model.SleepPayload;

public interface FitbitApi {

    @GET("/1/user/-/sleep/date/{date}.json")
    Call<SleepPayload> getSleepForDate(@Path("date") String date);
}

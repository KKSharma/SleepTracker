package sleeptracker.com.libfitbit.model.response;

import com.google.gson.annotations.SerializedName;

import sleeptracker.com.libfitbit.model.FitbitError;

public abstract class FitbitResult {
    @SerializedName("Error")
    public FitbitError error;

    public FitbitError getError() {
        return error;
    }

    public void setError(FitbitError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("FitbitResult{error=%s}", error == null ? "no error" : error.toString());
    }
}

package sleeptracker.com.libfitbit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class FitbitError implements Parcelable {

    @SuppressWarnings("unused")
    public static final Creator<FitbitError> CREATOR = new Creator<FitbitError>() {
        @Override
        public FitbitError createFromParcel(Parcel in) {
            return new FitbitError(in);
        }

        @Override
        public FitbitError[] newArray(int size) {
            return new FitbitError[size];
        }
    };
    @SerializedName("Details")
    private String details;
    @SerializedName("Message")
    private String message;
    @SerializedName("MessageEN")
    private String messageEN;
    @SerializedName("Number")
    private int number;

    @SuppressWarnings("unused")
    public FitbitError() {
    }


    protected FitbitError(Parcel in) {
        details = in.readString();
        message = in.readString();
        messageEN = in.readString();
        number = in.readInt();
    }

    @Override
    public String toString() {
        return "FitbitError{" +
                "details='" + details + '\'' +
                ", message='" + message + '\'' +
                ", messageEN='" + messageEN + '\'' +
                ", number=" + number +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(details);
        dest.writeString(message);
        dest.writeString(messageEN);
        dest.writeInt(number);
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageEN() {
        return messageEN;
    }

    public void setMessageEN(String messageEN) {
        this.messageEN = messageEN;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

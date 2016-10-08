package sleeptracker.com.libfitbit.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sleeptracker.com.libfitbit.api.FitbitApi;

public class FitbitClient {
    private static final String TAG = FitbitClient.class.getSimpleName();
    public static final String AUTHORIZATION = "Authorization";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final int READ_TIMEOUT = 10;
    public static final int CONNECT_TIMEOUT = 90;
    private static String PROTOCOL = "https://";
    private static FitbitClient sFitbitClient;
    private final Context mContext;
    private FitbitApi mFitbitApi;
    private static final int MAX_LOG_LENGTH = 2000;

    @NonNull
    private String mAuthToken = "";

    private Retrofit.Builder builder;

    private Retrofit retrofit;

    private FitbitClient(Context context, String buildVariant) {
        mContext = context;
        setupRestClient();
    }

    static Request addHeader(Request originalRequest, String key, String val) {
        return originalRequest.newBuilder().addHeader(key, val).build();
    }

    static Request replaceHeader(Request originalRequest, String key, String val) {
        if (originalRequest.header(key) == null) {
            return addHeader(originalRequest, key, val);
        } else {
            return originalRequest.newBuilder().header(key, val).build();
        }
    }

    public static FitbitClient getInstance() {
        return sFitbitClient;
    }

    public static FitbitClient init(Context context, String buildVariant) {
        if (sFitbitClient == null) {
            sFitbitClient = new FitbitClient(context, buildVariant);
        }
        return sFitbitClient;
    }

    // if key is already in header, do nothing. Otherwise add key->val to headers
    private Request addOrReplaceAuthToken(Request originalRequest) {
        String originalReqHeaderValue = originalRequest.header(AUTHORIZATION);
        if (originalReqHeaderValue == null) { // use default auth token
            return addHeader(originalRequest, AUTHORIZATION, getAuthToken());
        } else {
            return originalRequest;
        }
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(@NonNull String authToken) {
        Log.d("MatchClient", "setAuthToken=" + authToken);
        // never set auth token to empty string
        if (authToken.isEmpty()) {
            return;
        }
        mAuthToken = authToken;
    }

    private void setupApiImplementation(Retrofit retrofit) {
        if (retrofit != null) {
            mFitbitApi = retrofit.create(FitbitApi.class);
        }
    }

    private void setupRestClient() {
        OkHttpClient.Builder baseClientBuilder =
                new OkHttpClient().newBuilder()
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (message.length() <= MAX_LOG_LENGTH) {
                    Log.d(TAG, message);
                } else {
                    Log.d(TAG, message.substring(0, MAX_LOG_LENGTH));
                }

            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        baseClientBuilder.addInterceptor(interceptor);
        baseClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + getAuthToken())
                        .build();

                return chain.proceed(request);
            }
        });
        baseClientBuilder.addInterceptor(new FitBitInterceptor(true));
        builder = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(baseClientBuilder.build());
        retrofit = builder.build();


        setupApiImplementation(retrofit);
    }

    public void rebuildRestClient() throws Exception {
        builder = null;
        retrofit = null;
        setupRestClient();
    }

    public FitbitApi getMissedConnectionApi() {
        return mFitbitApi;
    }

    public String getBaseUrl() {
        return PROTOCOL + "api.fitbit.com" + "/";
    }

    class FitBitInterceptor implements Interceptor {
        private final boolean mIncludeToken;
        private FitBitInterceptor(boolean includeAuthToken) {
            mIncludeToken = includeAuthToken;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (mIncludeToken) {
                request = addOrReplaceAuthToken(request);
            }
            if (getAcceptLanguageHeader() != null) {
                request = addHeader(request, HEADER_ACCEPT_LANGUAGE, getAcceptLanguageHeader());
            }
            return chain.proceed(request);
        }

        private String getAcceptLanguageHeader() {
            return String.format(Locale.US, "%s-%s",
                    Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
        }
    }
}

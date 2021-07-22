package com.netpaisa.aepsriseinlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientAeps {

    private static Retrofit retrofit = null;
    private static final int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    //private static final String BASE_URL = "https://reseller.netpaisa.com/api/";

    private static final String BASE_URL = "https://www.netpaisa.com/nps/api/";

    private static  Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getClient(Context context) {
        mContext = context;
        if (okHttpClient == null)
            initOkHttp(context);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create( ))
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", AppConfig.HEAD_ACCEPT)
                        .addHeader("Content-Type", AppConfig.HEAD_CONTENT_TYPE)
                        .addHeader("SourceType", AppConfig.SOURCE_TYPE);

                Request request = requestBuilder.build();
                Log.e("Request :", request.toString());
                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();
    }
    public static ApiServiceAeps getClientService() { return ApiClientAeps.getClient(mContext).create(ApiServiceAeps.class); }
}



package com.crisppic.app.crisppic;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by d on 12.01.17.
 */

public interface CrisppicApi {
    @GET("login")
    Call<List<Object>> result();

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://774eaf29.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
}

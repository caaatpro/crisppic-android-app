package com.crisppic.app.crisppic;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestClient {
    @GET("people")
    Call<Object> basicLogin();
}
package com.crisppic.app.crisppic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestClient {
    @GET("login")
    Call<Object> basicLogin();

    @GET("user/profile")
    Call<User> profile();

    @GET("user/profile/movies")
    Call<List<UserMovies>> profileMovies();
}
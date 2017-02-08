package com.crisppic.app.crisppic;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestClient {
    @GET("login")
    Call<Object> basicLogin();

    @GET("user/profile")
    Call<User> profile();

    @GET("user/profile/movies")
    Call<List<UserMovies>> profileMovies();

    @GET("movies")
    Call<List<Movies>> movies();
}
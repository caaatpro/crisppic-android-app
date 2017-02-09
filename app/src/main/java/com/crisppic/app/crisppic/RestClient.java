package com.crisppic.app.crisppic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestClient {
    @GET("login")
    Call<Object> basicLogin();

    @GET("user/profile")
    Call<User> profile();

    @GET("user/profile/movies")
    Call<List<UserMovies>> profileMovies();

    @GET("movies")
    Call<List<Movies>> movies();

    @GET("movie/kinopoisk/{id}")
    Call<MovieKinopoisk> addKinopoisk(@Path("id") String id);
}
package com.crisppic.app.crisppic;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestClient {

    @GET("login")
    Call<Object> basicLogin(@Header("Authorization") String basic);

    @GET("user/profile")
    Call<User> profile();

    @GET("user/profile/movies")
    Call<List<UserMovies>> profileMovies();

    @PUT("user/movie/kinopoisk/{id}")
    Call<Object> userAddMovieKinopoisk(@Path("id") String id, @Query("date") Date date);

    @GET("movies")
    Call<List<Movie>> movies();

    @GET("movie/{id}")
    Call<Movie> movie(@Path("id") Integer id);

    @GET("movie/kinopoisk/{id}")
    Call<MovieKinopoisk> addKinopoisk(@Path("id") String id);


}
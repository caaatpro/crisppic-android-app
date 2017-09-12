package com.crisppic.app.crisppic;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestClient {

    @GET("login")
    Call<User> basicLogin(@Header("Authorization") String basic);

    @GET("user/{username}")
    Call<User> user(@Path("username") String username);

    @GET("user/{username}/views")
    Call<List<UserMovie>> userGetViews(@Path("username") String username);

    @GET("user/{username}/wants")
    Call<List<UserMovie>> userGetWants(@Path("username") String username);

    @PUT("user/views/kinopoisk/{id}")
    Call<Object> userViewKinopoisk(@Path("id") String id, @Query("date") Date date);

    @PUT("user/wants/kinopoisk/{id}")
    Call<Object> userWantKinopoisk(@Path("id") String id);

    @PUT("user/views/{sId}")
    Call<Object> userAddViews(@Path("sId") Integer sId);

    @PUT("user/wants/{sId}")
    Call<Object> userAddWants(@Path("sId") Integer sId);

    @DELETE("user/views/{sId}")
    Call<Object> userDelViews(@Path("sId") Integer sId);

    @DELETE("user/wants/{sId}")
    Call<Object> userDelWants(@Path("sId") Integer sId);

    @GET("movies")
    Call<List<Movie>> movies();

    @GET("movie/{id}")
    Call<Movie> movie(@Path("id") Integer id);

    @GET("movie/kinopoisk/{id}")
    Call<MovieKinopoisk> addKinopoisk(@Path("id") String id);


}
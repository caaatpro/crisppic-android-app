package com.crisppic.app.crisppic;

public class UserMovieModel {

    String title;
    String type;
    Integer sID;
    Integer moviesID;


    public UserMovieModel(String title, String type, Integer sID, Integer moviesID) {
        this.title = title;
        this.type = type;
        this.sID = sID;
        this.moviesID = moviesID;
    }


    public String getTitle() {
        return title;
    }


    public String getType() {
        return type;
    }

    public Integer getsID() {
        return sID;
    }

    public Integer getMoviesID() {
        return moviesID;
    }
}

package com.crisppic.app.crisppic;

public class MovieModel {

    String title;
    String type;


    public MovieModel(String title, String type ) {
        this.title = title;
        this.type = type;
    }


    public String getTitle() {
        return title;
    }


    public String getType() {
        return type;
    }


}

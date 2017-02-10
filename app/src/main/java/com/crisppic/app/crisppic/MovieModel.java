package com.crisppic.app.crisppic;

public class MovieModel {

    String title;
    String type;
    Integer sID;


    public MovieModel(String title, String type, Integer sID) {
        this.title = title;
        this.type = type;
        this.sID = sID;
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
}

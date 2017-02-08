package com.crisppic.app.crisppic;

/**
 * Created by anupamchugh on 09/02/16.
 */
public class DataModel {

    String name;
    String type;
    String feature;


    public DataModel(String name, String type, String feature ) {
        this.name=name;
        this.type=type;
        this.feature=feature;

    }


    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }

    public String getFeature() {
        return feature;
    }

}

package com.crisppic.app.crisppic;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d on 08.02.17.
 */
public class Movies {
    public Integer sID;
    public Integer runtime;
    public Integer year;
    public String type;
    public Date dateUpdate;

    public ArrayList<ratingObject> rating;

    public static class ratingObject {
        public String name;
        public String value;
    }

    public static ArrayList<titleObject> titles;

    public static Map<String,String> myMap = new HashMap<>();

    public static class titleObject {
        public String name;
        public String value;

        {
            myMap.put("this.name", titles.toString());
        }
    }

}

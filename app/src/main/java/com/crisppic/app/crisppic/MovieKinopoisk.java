package com.crisppic.app.crisppic;

import java.util.ArrayList;
import java.util.Date;

public class MovieKinopoisk {
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

    public titleObject titles;

    public class titleObject {
        public String russian;
        public String original;
    }

}

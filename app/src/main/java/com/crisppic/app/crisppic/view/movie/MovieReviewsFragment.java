package com.crisppic.app.crisppic.view.movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crisppic.app.crisppic.R;

public class MovieReviewsFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.movie_reviews_layout, container, false);


        return view;
    }
}

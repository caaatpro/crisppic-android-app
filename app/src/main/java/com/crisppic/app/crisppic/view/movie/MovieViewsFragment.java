package com.crisppic.app.crisppic.view.movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.crisppic.app.crisppic.R;

public class MovieViewsFragment extends Fragment implements View.OnClickListener {
    View view;
    Button b1,
           b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.movie_views_layout, container, false);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


}

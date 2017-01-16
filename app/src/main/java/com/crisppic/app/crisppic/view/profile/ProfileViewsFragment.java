package com.crisppic.app.crisppic.view.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crisppic.app.crisppic.R;

public class ProfileViewsFragment extends Fragment implements View.OnClickListener {
    View view;
    Button b1,
           b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_views_layout, container, false);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


}

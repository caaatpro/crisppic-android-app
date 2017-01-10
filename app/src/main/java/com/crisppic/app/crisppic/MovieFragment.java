package com.crisppic.app.crisppic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MovieFragment extends Fragment implements View.OnClickListener {
    View view;
    Button b1,
           b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.movie_layout, container, false);

        b1 = (Button) view.findViewById(R.id.b1);
        b1.setOnClickListener(this);

        b2 = (Button) view.findViewById(R.id.b2);
        b2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                CharSequence text = "Посмотрел!";
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                break;
            case R.id.b2:
                CharSequence text2 = "Буду!";
                Toast.makeText(getActivity(), text2, Toast.LENGTH_SHORT).show();
                break;
        }
    }


}

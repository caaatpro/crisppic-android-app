package com.crisppic.app.crisppic.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crisppic.app.crisppic.App;
import com.crisppic.app.crisppic.Movie;
import com.crisppic.app.crisppic.MovieAdapter;
import com.crisppic.app.crisppic.MovieModel;
import com.crisppic.app.crisppic.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {
    View view;

    private ArrayList<MovieModel> movieModel;

    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();


        view = inflater.inflate(R.layout.main_layout, container, false);

        App.getApi().movies().enqueue(new Callback<List<Movie>>() {
                         @Override
                         public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                             if (response.isSuccessful()) {
                                 List<Movie> movies = response.body();

                                 ListView listView = (ListView) view.findViewById(R.id.ListView2);

                                 movieModel = new ArrayList<>();

                                 for (int i = 0; i < movies.size(); i++) {
                                     String title = movies.get(i).titles.russian;
                                     if (movies.get(i).titles.original != "") {
                                         title += " (" + movies.get(i).titles.original + ") ";

                                     }
                                     title += movies.get(i).year;

                                     movieModel.add(new MovieModel(title, movies.get(i).type, movies.get(i).sID));

                                 }

                                 MovieAdapter adapter = new MovieAdapter(movieModel,context);

                                 listView.setAdapter(adapter);
                                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                         MovieModel movie = movieModel.get(position);

                                        Intent intent = new Intent(context, MovieActivity.class);
                                        intent.putExtra("sID", movie.getsID());

                                        startActivity(intent);
                                     }
                                 });

                             } else {
                                 // error response, no access to resource?
                                 // 404 or NotAuth
                                 Intent intent = new Intent(context, LoginActivity.class);
                                 startActivity(intent);
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Movie>> call, Throwable t) {
                             Log.d("Profile", String.valueOf(t));
                         }
                     }
        );

        return view;
    }


}

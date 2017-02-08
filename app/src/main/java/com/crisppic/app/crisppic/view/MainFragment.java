package com.crisppic.app.crisppic.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crisppic.app.crisppic.MovieAdapter;
import com.crisppic.app.crisppic.MovieModel;
import com.crisppic.app.crisppic.Movies;
import com.crisppic.app.crisppic.R;
import com.crisppic.app.crisppic.RestClient;
import com.crisppic.app.crisppic.service.RestClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.crisppic.app.crisppic.view.SplashActivity.PREFS_NAME;

public class MainFragment extends Fragment {
    View view;

    private RestClient loginService;

    private ArrayList<MovieModel> movieModel;

    private SharedPreferences settings;
    private String basic;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();

        // Restore preferences

        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        basic = settings.getString("basic", null);
        loginService =
                RestClientService.createService(RestClient.class, basic);

        view = inflater.inflate(R.layout.main_layout, container, false);

        Call<List<Movies>> call = loginService.movies();
        call.enqueue(new Callback<List<Movies>>() {
                         @Override
                         public void onResponse(Call<List<Movies>> call, Response<List<Movies>> response) {
                             Log.d("Profile", "onResponse");
                             if (response.isSuccessful()) {
                                 List<Movies> movies = response.body();

                                 ListView listView = (ListView) view.findViewById(R.id.ListView2);

                                 movieModel = new ArrayList<>();

                                 for (int i = 0; i < movies.size(); i++) {
//                                     for (int j = 0; j < movies.get(i).rating.size(); j++) {
//                                         Log.d("Profile", String.valueOf(j));
//                                         Log.d("Profile", String.valueOf(movies.get(i).rating.get(j).name));
//                                         Log.d("Profile", String.valueOf(movies.get(i).rating.get(j).value));
//                                     }
                                     Log.d("Profile", String.valueOf(movies.get(i).myMap.size()));
                                     movieModel.add(new MovieModel(movies.get(i).type, null));
                                 }

                                 MovieAdapter adapter = new MovieAdapter(movieModel,context);

                                 listView.setAdapter(adapter);
                                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                         MovieModel dataModel = movieModel.get(position);

                                         Snackbar.make(view, dataModel.getTitle()+"\n"+dataModel.getType(), Snackbar.LENGTH_LONG)
                                                 .setAction("No action", null).show();
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
                         public void onFailure(Call<List<Movies>> call, Throwable t) {
                             Log.d("Profile", String.valueOf(t));
                         }
                     }
        );

        return view;
    }
}

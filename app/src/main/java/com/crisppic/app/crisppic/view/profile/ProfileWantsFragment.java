package com.crisppic.app.crisppic.view.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crisppic.app.crisppic.App;
import com.crisppic.app.crisppic.R;
import com.crisppic.app.crisppic.UserMovie;
import com.crisppic.app.crisppic.UserMovieModel;
import com.crisppic.app.crisppic.UserWantsAdapter;
import com.crisppic.app.crisppic.view.LoginActivity;
import com.crisppic.app.crisppic.view.MovieActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileWantsFragment extends Fragment {
    View view;

    private ArrayList<UserMovieModel> movieModel;
    private Context context;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private void loadMovies() {
        App.getApi().userGetWants(App.username).enqueue(new Callback<List<UserMovie>>() {
            @Override
            public void onResponse(Call<List<UserMovie>> call, Response<List<UserMovie>> response) {
                if (response.isSuccessful()) {
                    List<UserMovie> movies = response.body();

                    ListView listView = (ListView) view.findViewById(R.id.WantsList);
                    registerForContextMenu(listView);

                    movieModel = new ArrayList<>();

                    for (int i = 0; i < movies.size(); i++) {
                        String title = movies.get(i).titles.russian;
                        if (movies.get(i).titles.original != "") {
                            title += " (" + movies.get(i).titles.original + ") ";

                        }
                        title += movies.get(i).year;

                        movieModel.add(new UserMovieModel(title, movies.get(i).type, movies.get(i).sID, movies.get(i).movie.sID));

                    }

                    UserWantsAdapter adapter = new UserWantsAdapter(movieModel,context);

                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            UserMovieModel movie = movieModel.get(position);

                            Intent intent = new Intent(context, MovieActivity.class);
                            intent.putExtra("sID", movie.getMoviesID());

                            startActivity(intent);
                        }
                    });

                    mSwipeRefreshLayout.setRefreshing(false);

                } else {
                    // error response, no access to resource?
                    // 404 or NotAuth
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<UserMovie>> call, Throwable t) {
                Log.d("Profile", String.valueOf(t));
            }
        }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_wants_layout, container, false);
        context = container.getContext();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                loadMovies();
            }
        });

        loadMovies();

        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_wants_movie, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // below variable info contains clicked item info and it can be null; scroll down to see a fix for it
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final UserMovieModel movie = movieModel.get(info.position);
        switch(item.getItemId()){
            case R.id.view:

                App.getApi()
                        .userAddViews(movie.getMoviesID())
                        .enqueue(new Callback<Object>() {
                                    @Override
                                    public void onResponse(Call<Object> call, Response<Object> response) {
                                        if (response.isSuccessful()) {
                                            Object res = response.body();

                                            App.getApi()
                                                    .userDelWants(movie.getsID())
                                                    .enqueue(new Callback<Object>() {
                                                                 @Override
                                                                 public void onResponse(Call<Object> call, Response<Object> response) {
                                                                     if (response.isSuccessful()) {
                                                                         Object res = response.body();


                                                                     } else {
                                                                         // error response, no access to resource?
                                                                         // 404 or NotAuth
                                                                         Intent intent = new Intent(context, LoginActivity.class);
                                                                         startActivity(intent);
                                                                     }
                                                                 }

                                                                 @Override
                                                                 public void onFailure(Call<Object> call, Throwable t) {
                                                                     Log.d("Profile", String.valueOf(t));
                                                                 }
                                                             }
                                                    );

                                        } else {
                                            // error response, no access to resource?
                                            // 404 or NotAuth
                                            Intent intent = new Intent(context, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Object> call, Throwable t) {
                                        Log.d("Profile", String.valueOf(t));
                                    }
                                }
                );
                return true;
            case R.id.remove:
                App.getApi()
                        .userDelViews(movie.getsID())
                        .enqueue(new Callback<Object>() {
                                     @Override
                                     public void onResponse(Call<Object> call, Response<Object> response) {
                                         if (response.isSuccessful()) {
                                             Object res = response.body();


                                         } else {
                                             // error response, no access to resource?
                                             // 404 or NotAuth
                                             Intent intent = new Intent(context, LoginActivity.class);
                                             startActivity(intent);
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<Object> call, Throwable t) {
                                         Log.d("Profile", String.valueOf(t));
                                     }
                                 }
                        );
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

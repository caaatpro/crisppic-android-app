package com.crisppic.app.crisppic.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.crisppic.app.crisppic.MovieAdapter;
import com.crisppic.app.crisppic.MovieKinopoisk;
import com.crisppic.app.crisppic.MovieModel;
import com.crisppic.app.crisppic.Movies;
import com.crisppic.app.crisppic.R;
import com.crisppic.app.crisppic.RestClient;
import com.crisppic.app.crisppic.service.RestClientService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.crisppic.app.crisppic.view.SplashActivity.PREFS_NAME;

public class AddKinopoiskActivity extends AppCompatActivity implements View.OnClickListener {
    private CharSequence toastText;
    private EditText MovieIdView;

    private Context context;

    private RestClient loginService;

    private SharedPreferences settings;
    private String basic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kinopoisk);

        context = getApplicationContext();

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        Button watched = (Button) findViewById(R.id.watched);
        watched.setOnClickListener(this);
        Button want = (Button) findViewById(R.id.want);
        want.setOnClickListener(this);

        MovieIdView = (EditText) findViewById(R.id.movieId);

        settings = this.getSharedPreferences(PREFS_NAME, 0);
        basic = settings.getString("basic", null);
        loginService =
                RestClientService.createService(RestClient.class, basic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                String id = MovieIdView.getText().toString();

                Log.d("Profile", id);
                if (TextUtils.isEmpty(id)) {
                    MovieIdView.setError("ID не указан :(");
                    MovieIdView.requestFocus();
                } else {
                    MovieIdView.setError(null);
                    toastText = "Добавляю в базу!";
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

                    Call<MovieKinopoisk> call = loginService.addKinopoisk(id);
                    call.enqueue(new Callback<MovieKinopoisk>() {
                                     @Override
                                     public void onResponse(Call<MovieKinopoisk> call, Response<MovieKinopoisk> response) {
                                         Log.d("Profile", "onResponse");
                                         if (response.isSuccessful()) {
                                             MovieKinopoisk movie = response.body();
                                             Log.d("Profile", String.valueOf(movie.titles));


                                             toastText = "Фильм добавлен в базу ˆˆ";
                                             Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
                                         } else {
                                             // error response, no access to resource?
                                             // 404 or NotAuth
                                             Intent intent = new Intent(context, LoginActivity.class);
                                             startActivity(intent);
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<MovieKinopoisk> call, Throwable t) {
                                         Log.d("Profile", String.valueOf(t));
                                     }
                                 }
                    );
                }
                break;
            case R.id.watched:
                toastText = "Добавляю в посмотренные!";
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
                break;
            case R.id.want:
                toastText = "Добавляю в хочу!";
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

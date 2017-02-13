package com.crisppic.app.crisppic.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.crisppic.app.crisppic.App;
import com.crisppic.app.crisppic.MovieKinopoisk;
import com.crisppic.app.crisppic.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKinopoiskActivity extends AppCompatActivity implements View.OnClickListener {
    private CharSequence toastText;
    private EditText MovieIdView;

    private Context context;


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

    }

    @Override
    public void onClick(View v) {
        final String id = MovieIdView.getText().toString();

        switch (v.getId()) {
            case R.id.add:
                if (TextUtils.isEmpty(id)) {
                    MovieIdView.setError("ID не указан :(");
                    MovieIdView.requestFocus();
                } else {
                    MovieIdView.setError(null);
                    toastText = "Добавляю в базу!";
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

                    App.getApi().addKinopoisk(id).enqueue(new Callback<MovieKinopoisk>() {
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

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                final int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddKinopoiskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int mouth, int day) {

                                String dateString = day + "/"
                                        + (mouth + 1) + "/" + year;

                                Toast.makeText(context, dateString, Toast.LENGTH_SHORT).show();

                                Date date = null;
                                DateFormat df = new SimpleDateFormat("d/MM/yyyy");
                                try{
                                    date = df.parse(dateString);
                                }
                                catch ( Exception ex ){
                                    System.out.println(ex);
                                }

                                addMovie(id, date);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Без даты", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Your code
                        addMovie(id, null);
                    }
                });
                datePickerDialog.show();

                break;
            case R.id.want:
                toastText = "Добавляю в хочу!";
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addMovie(String id, Date date) {
        App.getApi().userAddMovieKinopoisk(id, date).enqueue(new Callback<Object>() {
                 @Override
                 public void onResponse(Call<Object> call, Response<Object> response) {
                     if (response.isSuccessful()) {
                         Object req = response.body();
                         Log.d("Profile", String.valueOf(req));


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
                 public void onFailure(Call<Object> call, Throwable t) {
                     Log.d("Profile", String.valueOf(t));
                 }
             }
        );
    }
}

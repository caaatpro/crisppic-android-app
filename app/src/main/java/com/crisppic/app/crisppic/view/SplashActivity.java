package com.crisppic.app.crisppic.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.crisppic.app.crisppic.App;
import com.crisppic.app.crisppic.RestClient;
import com.crisppic.app.crisppic.service.RestClientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "UserPreferences";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        // Restore preferences
        Toast.makeText(context, App.basic, Toast.LENGTH_SHORT).show();

        if (App.basic == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            App.getApi().basicLogin().enqueue(new Callback<Object >() {
                             @Override
                             public void onResponse(Call<Object> call, Response<Object> response) {
                                 if (response.isSuccessful()) {
                                     // user object available
                                     Log.d("Error", response.body().toString());
                                     Log.d("Error1", "0");

                                     Intent intent = new Intent(context, MainActivity.class);
                                     startActivity(intent);
                                     finish();
                                 } else {
                                     // error response, no access to resource?
                                     // 404 or NotAuth
                                     Log.d("Error1", "1");

                                     Intent intent = new Intent(context, LoginActivity.class);
                                     startActivity(intent);
                                     finish();
                                 }
                             }

                             @Override
                             public void onFailure(Call<Object> call, Throwable t) {
                                 // something went completely south (like no internet connection)
                                 Log.d("Error", t.getMessage());

                                 Intent intent = new Intent(context, LoginActivity.class);
                                 startActivity(intent);
                                 finish();
                             }
                         }
            );
        }

    }
}
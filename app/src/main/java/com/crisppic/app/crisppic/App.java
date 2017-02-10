package com.crisppic.app.crisppic;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.crisppic.app.crisppic.service.RestClientService;

import static com.crisppic.app.crisppic.view.SplashActivity.PREFS_NAME;

public class App  extends Application {
    private SharedPreferences settings;
    public static String basic;

    public static void setBasic(String newBasic) {
        Log.d("Login", newBasic);
        basic = newBasic;
    }

    private static RestClient loginService;

    @Override
    public void onCreate() {
        super.onCreate();

        settings = this.getSharedPreferences(PREFS_NAME, 0);
        basic = settings.getString("basic", null);

        loginService = RestClientService.createService(RestClient.class);
    }

    public static RestClient getApi() {
        return loginService;
    }
}

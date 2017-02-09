package com.crisppic.app.crisppic;

import android.app.Application;
import android.content.SharedPreferences;

import com.crisppic.app.crisppic.service.RestClientService;

import static com.crisppic.app.crisppic.view.SplashActivity.PREFS_NAME;

public class App  extends Application {
    private SharedPreferences settings;
    public static String basic;

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

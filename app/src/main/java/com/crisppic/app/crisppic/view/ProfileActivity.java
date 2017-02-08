package com.crisppic.app.crisppic.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.crisppic.app.crisppic.CustomAdapter;
import com.crisppic.app.crisppic.DataModel;
import com.crisppic.app.crisppic.R;
import com.crisppic.app.crisppic.RestClient;
import com.crisppic.app.crisppic.User;
import com.crisppic.app.crisppic.UserMovies;
import com.crisppic.app.crisppic.service.RestClientService;
import com.crisppic.app.crisppic.view.profile.ProfileFragment;
import com.crisppic.app.crisppic.view.profile.ProfileViewsFragment;
import com.crisppic.app.crisppic.view.profile.ProfileWantsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.crisppic.app.crisppic.view.SplashActivity.PREFS_NAME;

public class ProfileActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Context context;

    private SharedPreferences settings;
    private String basic;
    private RestClient loginService;


    ArrayList<DataModel> dataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        context = getApplicationContext();

        // Restore preferences
        settings = getSharedPreferences(PREFS_NAME, 0);
        basic = settings.getString("basic", null);
        loginService =
                RestClientService.createService(RestClient.class, basic);
        Call<User> call = loginService.profile();
        call.enqueue(new Callback<User>() {
                         @Override
                         public void onResponse(Call<User> call, Response<User> response) {
                             if (response.isSuccessful()) {
                                 Log.d("Profile", response.body().username);

                                 actionBar.setTitle(response.body().username);
                                 actionBar.setSubtitle(response.body().username);

                                 TextView textView3 = (TextView) findViewById(R.id.textView3);
                                 textView3.setText(response.body().username);

                                 TextView textView4 = (TextView) findViewById(R.id.textView4);
                                 textView4.setText(response.body().username);

                                 setMovies();
                             } else {
                                 // error response, no access to resource?
                                 // 404 or NotAuth
                                 Intent intent = new Intent(context, LoginActivity.class);
                                 startActivity(intent);
                                 finish();
                             }
                         }

                         @Override
                         public void onFailure(Call<User> call, Throwable t) {

                         }
                     }
        );
    }

    private void setMovies() {
        Call<List<UserMovies>> call = loginService.profileMovies();
        call.enqueue(new Callback<List<UserMovies>>() {
                         @Override
                         public void onResponse(Call<List<UserMovies>> call, Response<List<UserMovies>> response) {
                             if (response.isSuccessful()) {
                                 List<UserMovies> userMovies = response.body();

                                 ListView listView=(ListView)findViewById(R.id.ListView);

                                 dataModels= new ArrayList<>();

                                 for (int i = 0; i < userMovies.size(); i++) {
                                     dataModels.add(new DataModel(String.valueOf(userMovies.get(i).sID), String.valueOf(userMovies.get(i).year), String.valueOf(userMovies.get(i).type)));
                                 }

                                 dataModels.add(new DataModel("Banana Bread", "Android 1.1","February 9, 2009"));
                                 dataModels.add(new DataModel("Cupcake", "Android 1.5", "April 27, 2009"));
                                 dataModels.add(new DataModel("Donut","Android 1.6","September 15, 2009"));
                                 dataModels.add(new DataModel("Eclair", "Android 2.0", "October 26, 2009"));
                                 dataModels.add(new DataModel("Froyo", "Android 2.2", "May 20, 2010"));
                                 dataModels.add(new DataModel("Gingerbread", "Android 2.3", "December 6, 2010"));
                                 dataModels.add(new DataModel("Honeycomb","Android 3.0","February 22, 2011"));
                                 dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "October 18, 2011"));
                                 dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "July 9, 2012"));
                                 dataModels.add(new DataModel("Kitkat", "Android 4.4", "October 31, 2013"));
                                 dataModels.add(new DataModel("Lollipop","Android 5.0", "November 12, 2014"));
                                 dataModels.add(new DataModel("Marshmallow", "Android 6.0", "October 5, 2015"));

                                 CustomAdapter adapter= new CustomAdapter(dataModels,getApplicationContext());

                                 listView.setAdapter(adapter);
                                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                         DataModel dataModel= dataModels.get(position);

                                         Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType(), Snackbar.LENGTH_LONG)
                                                 .setAction("No action", null).show();
                                     }
                                 });

                             } else {
                                 // error response, no access to resource?
                                 // 404 or NotAuth
                                 Intent intent = new Intent(context, LoginActivity.class);
                                 startActivity(intent);
                                 finish();
                             }
                         }

                         @Override
                         public void onFailure(Call<List<UserMovies>> call, Throwable t) {

                         }
                     }
        );
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setText("2342");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Профиль
                    ProfileFragment profile = new ProfileFragment();
                    return profile;
                case 1: // Просмотры
                    ProfileViewsFragment views = new ProfileViewsFragment();
                    return views;
                case 2: // Хочу посмотреть
                    ProfileWantsFragment wants = new ProfileWantsFragment();
                    return wants;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Профиль";
                case 1:
                    return "Просмотры";
                case 2:
                    return "Хочу посмотреть";
            }
            return null;
        }
    }
}

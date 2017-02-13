package com.crisppic.app.crisppic.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crisppic.app.crisppic.App;
import com.crisppic.app.crisppic.R;
import com.crisppic.app.crisppic.User;
import com.crisppic.app.crisppic.view.profile.ProfileFragment;
import com.crisppic.app.crisppic.view.profile.ProfileViewsFragment;
import com.crisppic.app.crisppic.view.profile.ProfileWantsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        App.getApi().user(App.username).enqueue(new Callback<User>() {
                         @Override
                         public void onResponse(Call<User> call, Response<User> response) {
                             if (response.isSuccessful()) {
                                 actionBar.setTitle(response.body().username);
                                 actionBar.setSubtitle(response.body().username);
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

package com.example.mijin.hue;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.mijin.hue.LoginTab.Tab1.LoginTabFragment1;
import com.example.mijin.hue.LoginTab.Tab2.LoginTabFragment2;
import com.example.mijin.hue.LoginTab.Tab3.LoginTabFragment3;

/**
 * Created by mijin on 2017-11-23.
 */


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    DrawerLayout drawerLayout;
    android.support.v4.app.Fragment fragment;
    Toolbar toolbar;
   // TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        //SharedPreferences.Editor editor = prefs.edit();
        //editor.putString("id","mijin054");

        Log.d("사용자",prefs.getString("id",null));

        setupToolbar();
        setupNavigationView();
       // setupTablayout();
        Bundle b;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Intent intent = getIntent();
        Intent returnIntent = new Intent();
        if(intent!=null) {
            if (intent.getParcelableExtra("new") != null) {
                setResult(Activity.RESULT_OK, returnIntent);

                b = new Bundle();
                b.putParcelable("new", intent.getParcelableExtra("new"));

                fragment = new LoginTabFragment3();
                fragment.setArguments(b);

                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(null);
                fragmentTransaction.commit();

            } else if (intent.getParcelableArrayListExtra("mem") != null) {
                setResult(Activity.RESULT_OK, returnIntent);

                b = new Bundle();
                b.putParcelableArrayList("mem", intent.getParcelableArrayListExtra("mem"));

                fragment = new LoginTabFragment1();
                fragment.setArguments(b);

                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            } else {

                fragment = new MainFragment();

                fragmentTransaction.add(R.id.frame, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        }



    }


    private void setupNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.main:
                fragment = new MainFragment();
                break;
            case R.id.navigation_item_1:
                fragment = new LoginTabFragment1();
                break;
            case R.id.navigation_item_2:
                fragment = new LoginTabFragment2();
                break;
            case R.id.navigation_item_3:
                fragment = new LoginTabFragment3();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

        return true;

    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);


    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }




/*
    private void setupTablayout(){

        tabLayout = (TabLayout) findViewById(tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
    }
  */


}


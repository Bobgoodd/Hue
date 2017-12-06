package com.example.mijin.hue.Day;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.mijin.hue.R;

/**
 * Created by mijin on 2017-11-25.
 */

public class Test extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        if(intent!=null&&intent.getStringExtra("setting")!=null){
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);

            Fragment fragment = new DayScheduleFragment();
            FrameLayout fram = (FrameLayout) findViewById(R.id.fram);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fram, new DayScheduleFragment());
            fragmentTransaction.commit();


        }else {
            FrameLayout fram = (FrameLayout) findViewById(R.id.fram);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fram, new DayScheduleFragment());
            fragmentTransaction.commit();
        }
    }

}

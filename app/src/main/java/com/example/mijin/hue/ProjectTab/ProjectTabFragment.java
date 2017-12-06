package com.example.mijin.hue.ProjectTab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mijin.hue.R;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by mijin on 2017-11-24.
 */

public class ProjectTabFragment extends Fragment{
    ViewPager viewPager;
    TabLayout tabLayout;
    ProjectTabPagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_projecttab,container,false);
        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);

        Log.d("시작시간",prefs.getString("start",null));
        Log.d("종료시간",prefs.getString("end",null));

        // Adding Toolbar to the activity
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // Initializing the TabLayout
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorGray),getResources().getColor(R.color.colorLogo));
        //tabLayout.addTab(tabLayout.newTab().setText("회의방"));
        tabLayout.addTab(tabLayout.newTab().setText("document"));
        tabLayout.addTab(tabLayout.newTab().setText("todo"));
        tabLayout.addTab(tabLayout.newTab().setText("schedule"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MeetingActivity.class);
                startActivity(intent);
            }
        });


        // Initializing ViewPager
        viewPager = (ViewPager) view.findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        pagerAdapter = new ProjectTabPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        //viewPager.setAdapter(new ProjectTabPagerAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
/*
        Bundle b = getArguments();
        if(b!=null){
            if(b.getBoolean("workadd")==true||b.getBoolean("workedit")==true){
                viewPager.setCurrentItem(1);
            }
        }

*/
        /*
        Fragment f;
        Bundle b = new Bundle();
        Intent intent = getIntent();


        f = pagerAdapter.getItem(0);
        b.putString("project_id",intent.getStringExtra("project_id"));
        if(b==null) Toast.makeText(getApplicationContext(), "데이터 없음", Toast.LENGTH_LONG).show();
        else{
            Toast.makeText(getApplicationContext(), "데이터 있음", Toast.LENGTH_LONG).show();
            f.setArguments(b);
        }
        */
        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                pagerAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }

}

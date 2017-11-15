package com.example.mijin.hue.ProjectTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.mijin.hue.ProjectTab.Tab1.ProjectTabFragment1;
import com.example.mijin.hue.R;

/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabActivity extends AppCompatActivity implements ProjectTabFragment1.OnMyListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projecttab);



        // Adding Toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("회의방"));
        tabLayout.addTab(tabLayout.newTab().setText("문서"));
        tabLayout.addTab(tabLayout.newTab().setText("업무"));
        tabLayout.addTab(tabLayout.newTab().setText("프로젝트 일정"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        ProjectTabPagerAdapter pagerAdapter = new ProjectTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

            }
        });

    }


    @Override
    public void onRecievedData(Object data) {
        Toast.makeText(getApplicationContext(), data.toString()+"성공", Toast.LENGTH_LONG ).show();
    }
}

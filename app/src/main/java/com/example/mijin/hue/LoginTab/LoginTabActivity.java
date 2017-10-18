package com.example.mijin.hue.LoginTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.mijin.hue.LoginTab.Tab3.LoginTabFragment3;
import com.example.mijin.hue.R;

/**
 * Created by mijin on 2017-10-03.
 */

public class LoginTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    LoginTabFragment3 tabF3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logintab);

        // Adding Toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout1);
        tabLayout.addTab(tabLayout.newTab().setText("Tab One"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab Two"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab Three"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager1);

        // Creating TabPagerAdapter adapter
        LoginTabPagerAdapter pagerAdapter = new LoginTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
}

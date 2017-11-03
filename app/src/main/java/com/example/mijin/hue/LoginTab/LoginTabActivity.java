package com.example.mijin.hue.LoginTab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.mijin.hue.LoginTab.Tab3.FriendViewItem;
import com.example.mijin.hue.R;

import java.util.ArrayList;


/**
 * Created by mijin on 2017-10-03.
 */

public class LoginTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LoginTabPagerAdapter pagerAdapter;

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
        pagerAdapter = new LoginTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

 /*
        Fragment f;
        Bundle b = new Bundle();
        Intent intent = getIntent();

        if(intent.getBooleanExtra("newactivity",false)==true){

            viewPager.setCurrentItem(2);
            f = pagerAdapter.getItem(2);
            b.putParcelable("new",intent.getParcelableExtra("new"));
            if(b==null) Toast.makeText(getApplicationContext(), "데이터 없음", Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(getApplicationContext(), "데이터 있음", Toast.LENGTH_LONG).show();
                f.setArguments(b);
            }
        }

        b.clear();
        */


        Intent intent = getIntent();

        FriendViewItem item = intent.getParcelableExtra("new");
        ArrayList<FriendViewItem> items = intent.getParcelableArrayListExtra("mem");
        Bundle b = new Bundle();

        if(item!=null){
            viewPager.setCurrentItem(2);
            b.putParcelable("new", item);
            pagerAdapter.getItem(2).setArguments(b);
        }

        if(items!=null) {
            viewPager.setCurrentItem(0);
            b.putParcelableArrayList("mem",items);
            pagerAdapter.getItem(0).setArguments(b);
        }


        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        pagerAdapter.notifyDataSetChanged();
    }


}

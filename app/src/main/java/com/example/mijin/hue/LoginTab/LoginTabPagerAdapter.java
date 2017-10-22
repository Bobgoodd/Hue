package com.example.mijin.hue.LoginTab;

/**
 * Created by mijin on 2017-10-03.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mijin.hue.LoginTab.Tab1.LoginTabFragment1;
import com.example.mijin.hue.LoginTab.Tab2.LoginTabFragment2;
import com.example.mijin.hue.LoginTab.Tab3.LoginTabFragment3;

import java.util.ArrayList;
import java.util.List;


public class LoginTabPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private int tabCount;
    private List<Fragment> tabList = new ArrayList<Fragment>();


    public LoginTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fm = fm;
        this.tabCount = tabCount;

        this.tabList.add(new LoginTabFragment1());
        this.tabList.add(new LoginTabFragment2());
        this.tabList.add(new LoginTabFragment3());

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return tabList.get(0);
            case 1: return tabList.get(1);
            case 2: return tabList.get(2);
            default:
                return null;
        }



    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
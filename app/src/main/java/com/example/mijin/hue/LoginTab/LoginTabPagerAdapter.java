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


public class LoginTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public LoginTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: LoginTabFragment1 tabFragment1 = new LoginTabFragment1();
                return tabFragment1;
            case 1: LoginTabFragment2 tabFragment2 = new LoginTabFragment2();
                return tabFragment2;
            case 2: LoginTabFragment3 tabFragment3 = new LoginTabFragment3();
                return tabFragment3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
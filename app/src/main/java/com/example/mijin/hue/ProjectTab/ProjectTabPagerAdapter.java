package com.example.mijin.hue.ProjectTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mijin.hue.ProjectTab.Tab1.ProjectTabFragment1;
import com.example.mijin.hue.ProjectTab.Tab2.ProjectTabFragment2;
import com.example.mijin.hue.ProjectTab.Tab3.ProjectTabFragment3;
import com.example.mijin.hue.ProjectTab.Tab4.ProjectTabFragment4;

/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public ProjectTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProjectTabFragment1 tabFragment1 = new ProjectTabFragment1();
                return tabFragment1;
            case 1:
                ProjectTabFragment2 tabFragment2 = new ProjectTabFragment2();
                return tabFragment2;
            case 2:
                ProjectTabFragment3 tabFragment3 = new ProjectTabFragment3();
                return tabFragment3;
            case 3:
                ProjectTabFragment4 tabFragment4 = new ProjectTabFragment4();
                return tabFragment4;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

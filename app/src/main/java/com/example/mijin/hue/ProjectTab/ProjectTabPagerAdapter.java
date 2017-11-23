package com.example.mijin.hue.ProjectTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mijin.hue.ProjectTab.Tab2.ProjectTabFragment2;
import com.example.mijin.hue.ProjectTab.Tab3.ProjectTabFragment3;
import com.example.mijin.hue.ProjectTab.Tab4.ProjectTabFragment4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private int tabCount;
    private List<Fragment> tabList = new ArrayList<Fragment>();

    public ProjectTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);

        this.fm = fm;
        this.tabCount = tabCount;

        //this.tabList.add(new ProjectTabFragment1());
        this.tabList.add(new ProjectTabFragment2());
        this.tabList.add(new ProjectTabFragment3());
        this.tabList.add(new ProjectTabFragment4());

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return tabList.get(0);
            case 1: return tabList.get(1);
            case 2: return tabList.get(2);
            //case 3: return tabList.get(3);
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

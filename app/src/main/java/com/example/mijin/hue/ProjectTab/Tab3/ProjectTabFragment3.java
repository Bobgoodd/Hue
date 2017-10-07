package com.example.mijin.hue.ProjectTab.Tab3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mijin.hue.R;

import java.util.Date;

/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabFragment3 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View tab3 = inflater.inflate(R.layout.fragment_project_tab3, container, false);

        ListView listView = (ListView) tab3.findViewById(R.id.workList);
        WorkViewAdapter adapter = new WorkViewAdapter();

        listView.setAdapter(adapter);

        adapter.addItem("업무#1", new Date(), "ddd, dddd", 20);
        adapter.addItem("업무#2", new Date(), "aa, dd, ddd", 66);
        adapter.addItem("업무#3", new Date(), "dd", 30);
        adapter.addItem("업무#4", new Date(), "dd", 28);
        adapter.addItem("업무#5", new Date(), "dd", 80);
        adapter.addItem("업무#6", new Date(), "dd", 50);

        return tab3;
    }
}

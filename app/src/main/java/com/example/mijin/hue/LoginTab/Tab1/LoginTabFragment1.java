package com.example.mijin.hue.LoginTab.Tab1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mijin.hue.ProjectTab.ProjectTabActivity;
import com.example.mijin.hue.R;

import java.util.Date;


public class LoginTabFragment1 extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_tab1, container, false);


        ListView listView;

        ProjectViewAdapter adapter;
        adapter = new ProjectViewAdapter();


        listView = (ListView) view.findViewById(R.id.projectList);
        listView.setAdapter(adapter);


        adapter.addItem("프로젝트#1", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#2", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#3", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#4", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#5", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#6", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#7", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#8", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#9", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#10", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#11", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#12", new Date(), "bobgood, dddd");
        adapter.addItem("프로젝트#13", new Date(), "bobgood, dddd");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ProjectTabActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

package com.example.mijin.hue.ProjectTab.Tab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mijin.hue.R;

import java.util.Date;


/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabFragment2 extends Fragment {

    View tab2;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        tab2 = inflater.inflate(R.layout.fragment_project_tab2, container, false);

        ListView listView = (ListView) tab2.findViewById(R.id.documentList);
        DocumentViewAdapter adapter;
        adapter = new DocumentViewAdapter();

        listView.setAdapter(adapter);

        adapter.addItem("회의문서#10", new Date(), new Date(), "dd, aa, cc");
        adapter.addItem("회의문서#9", new Date(), new Date(), "dd, cc");
        adapter.addItem("회의문서#8", new Date(), new Date(), "dd");
        adapter.addItem("회의문서#7", new Date(), new Date(), "dd");
        adapter.addItem("회의문서#6", new Date(), new Date(), "dd");
        adapter.addItem("회의문서#5", new Date(), new Date(), "dd");
        adapter.addItem("회의문서#4", new Date(), new Date(), "dd");
        adapter.addItem("회의문서#3", new Date(), new Date(), "dd");
        adapter.addItem("회의문서#2", new Date(), new Date(), "dd");
        adapter.addItem("회의문서#1", new Date(), new Date(), "dd");



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(tab2.getContext(), DocumentDetailActivity.class);
                startActivity(intent);
            }
        });

        return tab2;
    }
}
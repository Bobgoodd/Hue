package com.example.mijin.hue.LoginTab.Tab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mijin.hue.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mijin on 2017-10-02.
 */

public class ProjectViewAdapter extends BaseAdapter {

    private ArrayList<ProjectViewItem> projectViewList = new ArrayList<ProjectViewItem>();

    public ProjectViewAdapter() {
    }

    @Override
    public int getCount() {
        return projectViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return projectViewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.projectview_item, viewGroup, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView projectName = (TextView) view.findViewById(R.id.projectName);
        TextView createdTime = (TextView) view.findViewById(R.id.createdTime);
        TextView participatedID = (TextView) view.findViewById(R.id.participatedID);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ProjectViewItem projectViewItem = projectViewList.get(i);

        // 아이템 내 각 위젯에 데이터 반영
        projectName.setText(projectViewItem.getProjectName());
        createdTime.setText(projectViewItem.getCreatedTime().toString());
        participatedID.setText(projectViewItem.getParticipatedID());

        return view;



    }

    public void addItem(String projectName, Date createdTime, String participatedID) {
        ProjectViewItem projectViewItem = new ProjectViewItem();

        projectViewItem.setProjectName(projectName);
        projectViewItem.setCreatedTime(createdTime);
        projectViewItem.setParticipatedID(participatedID);

        projectViewList.add(projectViewItem);
    }

}

package com.example.mijin.hue.ProjectTab.Tab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mijin.hue.R;

import java.util.ArrayList;
import java.util.Date;

;

/**
 * Created by mijin on 2017-10-03.
 */

public class WorkViewAdapter extends BaseAdapter{

    ArrayList<WorkViewItem> workViewList = new ArrayList<WorkViewItem>();

    @Override
    public int getCount() {
        return workViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return workViewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int pos = i;
        final Context context = viewGroup.getContext();

        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.workview_item, viewGroup, false);
        }

        TextView workName = (TextView) view.findViewById(R.id.workName);
        TextView dueTime = (TextView) view.findViewById(R.id.dueTime);
        TextView d_day = (TextView) view.findViewById(R.id.d_day);
        TextView wparticipationId = (TextView) view.findViewById(R.id.wparticipatedId);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        TextView progressBarStatus = (TextView) view.findViewById(R.id.progressBarStatus);

        WorkViewItem workViewItem = workViewList.get(i);

        workName.setText(workViewItem.getWorkName());
        dueTime.setText(workViewItem.getDueTime().toString());
        d_day.setText("(D-"+workViewItem.getD_day()+")");
        wparticipationId.setText(workViewItem.getParticipationId());
        progressBar.setProgress(workViewItem.getProgress());
        progressBarStatus.setText(workViewItem.getProgress()+"%");

        return view;

    }

    public void addItem(String name, Date dueTime, String participationId, int progress){
        WorkViewItem workViewItem = new WorkViewItem();

        workViewItem.setWorkName(name);
        workViewItem.setDueTime(dueTime);
        workViewItem.setD_day((int) ((Math.random()+1)*100));
        workViewItem.setParticipationId(participationId);
        workViewItem.setProgress(progress);

        workViewList.add(workViewItem);
    }
}

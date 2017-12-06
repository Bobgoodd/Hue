package com.example.mijin.hue.ProjectTab.Tab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.example.mijin.hue.R;

import java.util.ArrayList;

;

/**
 * Created by mijin on 2017-10-03.
 */

public class WorkViewAdapter extends BaseAdapter{

    int _id;
    String item;
    int progress;
    ArrayList<WorkViewItem> workViewList = new ArrayList<WorkViewItem>();
    View.OnClickListener mOnclickListener;


    public WorkViewAdapter(View.OnClickListener mOnclickListener) {
        this.mOnclickListener = mOnclickListener;
    }
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

        _id = ((WorkViewItem)getItem(i)).getId();
        item = ((WorkViewItem)getItem(i)).getItem();
        progress = ((WorkViewItem)getItem(i)).getProgress();
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.workview_item, viewGroup, false);
        }
        SwipeLayout swipeLayout =  (SwipeLayout) view.findViewById(R.id.swipeLayout);

//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, view.findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SimpleSwipeListener(){
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
            }
        });


        TextView workName = (TextView) view.findViewById(R.id.workName);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        TextView progressBarStatus = (TextView) view.findViewById(R.id.progressBarStatus);




        WorkViewItem workViewItem = workViewList.get(i);

        int id = workViewItem.getId();
        String item = workViewItem.getItem();
        int progress = workViewItem.getProgress();

        workName.setText(workViewItem.getItem());
        progressBar.setProgress(workViewItem.getProgress());
        progressBarStatus.setText(workViewItem.getProgress()+"%");

        Button modify = (Button) view.findViewById(R.id.wmodify);
        Button delete = (Button) view.findViewById(R.id.wdelete);

        modify.setOnClickListener(mOnclickListener);
        modify.setTag(R.string.tag,id);
        modify.setTag(R.string.tag1,item);
        modify.setTag(R.string.tag2,progress);

        delete.setOnClickListener(mOnclickListener);
        delete.setTag(R.string.tag,id);

        return view;

    }

    public void addItem(int id, String item, int progress){
        WorkViewItem workViewItem = new WorkViewItem();

        workViewItem.setId(id);
        workViewItem.setItem(item);
        workViewItem.setProgress(progress);

        workViewList.add(workViewItem);
    }

    public void clear(){
        workViewList.clear();
    }

}

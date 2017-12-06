package com.example.mijin.hue.LoginTab.Tab1;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import java.util.ArrayList;

/**
 * Created by mijin on 2017-10-02.
 */

public class ProjectViewAdapter extends BaseAdapter {

    private ArrayList<ProjectViewItem> projectViewList = new ArrayList<ProjectViewItem>();
    String url;
    ContentValues values;
    ProjectViewItem projectViewItem;
    NetworkTask4 networkTask4;
    View.OnClickListener mOnclickListener;


    public ProjectViewAdapter(View.OnClickListener mOnclickListener) {
        this.mOnclickListener = mOnclickListener;
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



        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final TextView projectName = (TextView) view.findViewById(R.id.projectName);
        TextView createdTime = (TextView) view.findViewById(R.id.createdTime);
        //TextView participatedID = (TextView) view.findViewById(R.id.participatedID);




        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        projectViewItem = projectViewList.get(i);

        int project_id = projectViewItem.getProjectid();

        // 아이템 내 각 위젯에 데이터 반영
        projectName.setText(projectViewItem.getProjectName().toString());
        createdTime.setText(projectViewItem.getCreatedTime().toString());
        //participatedID.setText(projectViewItem.getParticipatedID().toString());



        Button delete = (Button)swipeLayout.findViewById(R.id.delete);
        delete.setTag(R.string.tag, project_id);
        delete.setOnClickListener(mOnclickListener);


        Button modify = (Button)swipeLayout.findViewById(R.id.modify);
        modify.setTag(R.string.tag, project_id);
        modify.setOnClickListener(mOnclickListener);

        return view;



    }

    public void addItem(int id, String projectName, String createdTime, String participatedID) {
        ProjectViewItem projectViewItem = new ProjectViewItem();

        projectViewItem.setProjectid(id);
        projectViewItem.setProjectName(projectName);
        projectViewItem.setCreatedTime(createdTime);
        projectViewItem.setParticipatedID(participatedID);

        projectViewList.add(projectViewItem);
    }

    public void clear(){
        projectViewList.clear();
    }
    public class NetworkTask4 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;


        public NetworkTask4(String url, ContentValues values) {

        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params) {

        String result;
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values);

        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        notifyDataSetChanged();

    }
}
}

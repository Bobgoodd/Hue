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
    Button delete, modify;
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
            delete = (Button) view.findViewById(R.id.delete);
            modify = (Button) view.findViewById(R.id.modify);


            delete.setOnClickListener(mOnclickListener);
            modify.setOnClickListener(mOnclickListener);
            delete.setTag(R.string.tag,projectViewList.get(i).getProjectid());
            modify.setTag(R.string.tag1,projectViewList.get(i).getProjectid());



        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final TextView projectName = (TextView) view.findViewById(R.id.projectName);
        TextView createdTime = (TextView) view.findViewById(R.id.createdTime);
        TextView participatedID = (TextView) view.findViewById(R.id.participatedID);




        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        projectViewItem = projectViewList.get(i);

        // 아이템 내 각 위젯에 데이터 반영
        projectName.setText(projectViewItem.getProjectName());
        createdTime.setText(projectViewItem.getCreatedTime().toString());
        participatedID.setText(projectViewItem.getParticipatedID());



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

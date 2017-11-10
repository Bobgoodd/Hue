package com.example.mijin.hue.LoginTab.Tab1;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mijin.hue.ProjectTab.ProjectTabActivity;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginTabFragment1 extends Fragment {

    View view;
    ProjectViewAdapter adapter;
    String url;
    ContentValues values;
    NetworkTask3 networkTask3;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_tab1, container, false);





        adapter = new ProjectViewAdapter(OnclickListener);


        if(getArguments()==null) Toast.makeText(getContext(), "bundle null", Toast.LENGTH_LONG).show();
        else {/*
            ArrayList<FriendViewItem> items = getArguments().getParcelableArrayList("mem");
            String memId = "";
            if (items != null) {
                for (FriendViewItem item : items) {
                    memId += item.getId() + " ";
                }
            }

            adapter.addItem("프로젝트", new Date(), memId);
            */

        }

        url =  "http://uoshue.dothome.co.kr/loadProject.php?";
        values = new ContentValues();
        values.put("director_id","11");
        networkTask3 = new NetworkTask3(url, values);
        networkTask3.execute();
        adapter.notifyDataSetChanged();

        listView = (ListView) view.findViewById(R.id.projectList);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ProjectTabActivity.class);
                startActivity(intent);
            }
        });


        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProjectActivity.class);
                startActivity(intent);


            }
        });


        return view;
    }

    public ProjectViewAdapter getAdapter() {
        return adapter;
    }

    public class NetworkTask3 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask3(String url, ContentValues values) {

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

            try {
                if(result!=null) {
                    JSONArray jarr = new JSONArray(result);

                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = (JSONObject) jarr.get(i);
                        // 접근한 회원테이블에 id가 있는지 있으면 그 튜플 반환
                        // 튜플로부터 데이터를 뽑아 어댑터에 추가

                        String memlist="";
                        /*
                        boolean flag=false;
                        JSONArray jarr2 = (JSONArray) ((JSONObject) jarr.get(i)).getJSONArray("memlist");
                        for(int j=0;j<jarr2.length();j++){
                            JSONObject json2 = (JSONObject) jarr2.get(i);
                            if(flag) memlist += ",";
                            else memlist += json2.getString("");
                            flag=true;
                        }
                        */

                        memlist = json.get("memlist").toString().trim();

                        String[] spl = memlist.split("[\"\\[\\]]+");
                        memlist = "";
                        for(String str : spl){
                            memlist += str+" ";
                        }
                        adapter.addItem(json.getInt("id"), json.getString("name"), json.getString("created"), memlist);
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();

        }
    }


    final View.OnClickListener OnclickListener = new View.OnClickListener() {

        public void onClick(View v) {


            Log.d("MainActivity OnClick테스트", "test: vid:" + v.getId() + "tag:" + v.getTag());
            //추가버튼 switch로 바꾸도록

            switch (v.getId()) {

                case R.id.delete:
                    url = "http://uoshue.dothome.co.kr/deleteProject.php?";
                    values = new ContentValues();
                    values.put("project_id", String.valueOf(v.getTag(R.string.tag)));
                    networkTask3 = new NetworkTask3(url, values);
                    networkTask3.execute();

                    adapter.notifyDataSetChanged();

                    break;


                case R.id.modify:


                    break;


                default:

                    break;

            }

        }
    };
}

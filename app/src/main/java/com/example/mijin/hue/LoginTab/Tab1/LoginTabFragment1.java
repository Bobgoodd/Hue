package com.example.mijin.hue.LoginTab.Tab1;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mijin.hue.ProjectTab.ProjectTabFragment;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class LoginTabFragment1 extends Fragment {

    View view;
    ProjectViewAdapter adapter;
    String url;
    ContentValues values;
    NetworkTask3 networkTask3;
    ListView listView;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String id, project_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_tab1, container, false);


        prefs=getActivity().getSharedPreferences("PrefName",MODE_PRIVATE);
        editor = prefs.edit();

        adapter = new ProjectViewAdapter(OnclickListener);

/*
        if(getArguments()==null) Toast.makeText(getContext(), "bundle null", Toast.LENGTH_LONG).show();
        else {

            ArrayList<FriendViewItem> items = getArguments().getParcelableArrayList("mem");
            String memId = "";
            if (items != null) {
                for (FriendViewItem item : items) {
                    memId += item.getId() + " ";
                }
            }

            adapter.addItem("프로젝트", new Date(), memId);

            ArrayList<FriendViewItem> items = getArguments().getParcelableArrayList("mem");
            String index = getArguments().getString("project_id");

            if(items!=null){
                if(index!=null) {
                    String orimemlist = ((ProjectViewItem) adapter.getItem(Integer.parseInt("index"))).getParticipatedID();
                    for (FriendViewItem item : items) {
                        orimemlist += ", " + item.getId();
                    }
                    ((ProjectViewItem) adapter.getItem(Integer.parseInt("index"))).setParticipatedID(orimemlist);
                    adapter.notifyDataSetChanged();
                }
            }

        }
*/
        url =  "http://uoshue.dothome.co.kr/loadProject.php?";
        values = new ContentValues();

        if(prefs!=null){
            id = prefs.getString("id", null);
            Log.d("로그인정보",id);
        }

        values.put("director_id",id);
        networkTask3 = new NetworkTask3(url, values);
        networkTask3.execute();
        adapter.notifyDataSetChanged();

        listView = (ListView) view.findViewById(R.id.projectList);
        listView.setAdapter(adapter);
/*
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String project_id = String.valueOf(((ProjectViewItem)adapterView.getAdapter().getItem(i)).getProjectid());

                editor.putString("project_id",String.valueOf(((ProjectViewItem)adapterView.getAdapter().getItem(i)).getProjectid()));
                editor.commit();
                Log.d("들어가라",prefs.getString("project_id",null));

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, new ProjectTabFragment());
                fragmentTransaction.commit();

                /*
                Intent intent = new Intent(view.getContext(), ProjectTabActivity.class);
                //intent.putExtra("project_id", project_id);
                startActivity(intent);
                */
            }
        });
/*

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProjectActivity.class);
                startActivity(intent);

            }
        });

        */

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
                if(result!=null&&!result.equals("")) {

                    JSONArray jarr = new JSONArray(result);
                    String memlist = "";
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = (JSONObject) jarr.get(i);
                        // 접근한 회원테이블에 id가 있는지 있으면 그 튜플 반환
                        // 튜플로부터 데이터를 뽑아 어댑터에 추가


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
                        for (String str : spl) {
                            memlist += str + " ";
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
                    project_id=String.valueOf(v.getTag(R.string.tag));
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("삭제 하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            url = "http://uoshue.dothome.co.kr/deleteProject.php?";
                            values = new ContentValues();
                            values.put("usr_id",prefs.getString("id",null));
                            values.put("project_id", project_id);
                            networkTask3 = new NetworkTask3(url, values);
                            networkTask3.execute();
                            adapter.notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("취소",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();




                    break;


                case R.id.modify:
                    /*
                    url = "http://uoshue.dothome.co.kr/modify.php?";
                    values = new ContentValues();
                    values.put("project_id", String.valueOf(v.getTag(R.string.tag)));
                    networkTask3 = new NetworkTask3(url, values);
                    networkTask3.execute();

                    adapter.notifyDataSetChanged();
*/
                    project_id=String.valueOf(v.getTag(R.string.tag1));
                    Intent intent2 = new Intent(getActivity(), ModifyProjectActivity.class);
                    intent2.putExtra("project_id",project_id);
                    startActivity(intent2);

                    break;


                default:

                    break;

            }

        }
    };
}

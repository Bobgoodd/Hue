package com.example.mijin.hue.ProjectTab.Tab3;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabFragment3 extends Fragment {

    int wid, progress;
    String item, usr_id, project_id;
    String url;
    ContentValues values;
    NetworkTask34 networkTask34;
    WorkViewAdapter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View tab3 = inflater.inflate(R.layout.fragment_project_tab3, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);
        usr_id = prefs.getString("id",null);
        project_id = prefs.getString("project_id",null);


        final ListView listView = (ListView) tab3.findViewById(R.id.workList);
        adapter = new WorkViewAdapter(OnclickListener);

        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) tab3.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditTodoActivity.class);
                intent.putExtra("isProjectTab3",true);
                startActivityForResult(intent,20);
            }
        });

        load();

        return tab3;
    }

    public class NetworkTask34 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;


        public NetworkTask34(String url, ContentValues values) {

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

            if(result!=null){
                try {
                    adapter.clear();
                    JSONArray jarr = new JSONArray(result);
                    for(int i=0;i<jarr.length();i++){
                        JSONObject json = (JSONObject) jarr.get(i);

                        adapter.addItem(json.getInt("id"), json.getString("item"), json.getInt("progress"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            adapter.notifyDataSetChanged();

        }
    }

        final View.OnClickListener OnclickListener = new View.OnClickListener() {

            public void onClick(View v) {


                Log.d("WorkView OnClick테스트", "test: vid:" + v.getId() + "tag:" + v.getTag());
                //추가버튼 switch로 바꾸도록


                switch (v.getId()) {

                    case R.id.wdelete:
                        wid = (Integer) v.getTag(R.string.tag);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyAlertDialog);
                        builder.setMessage("삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                url = "http://uoshue.dothome.co.kr/deleteTask.php?";
                                values = new ContentValues();
                                values.put("id",wid);
                                networkTask34 = new NetworkTask34(url,values);
                                networkTask34.execute();

                                load();
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();




                        break;


                    case R.id.wmodify:

                        wid = (Integer) v.getTag(R.string.tag);
                        item = (String) v.getTag(R.string.tag1);
                        progress = (Integer) v.getTag(R.string.tag2);
                        Intent intent = new Intent(getContext(), EditTodoActivity.class);
                        intent.putExtra("id",wid);
                        intent.putExtra("item", item);
                        intent.putExtra("progress", progress);
                        startActivityForResult(intent,20);

                        break;


                    default:

                        break;

                }

            }
        };

        public void load(){
            url = "http://uoshue.dothome.co.kr/loadTask.php?";
            values = new ContentValues();
            values.put("usr_id",usr_id);
            values.put("project_id",project_id);
            networkTask34 = new NetworkTask34(url,values);
            networkTask34.execute();
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode==20&&requestCode==RESULT_OK)
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}

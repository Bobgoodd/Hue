package com.example.mijin.hue.ProjectTab.Tab2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabFragment2 extends Fragment {

    View tab2;
    DocumentViewAdapter adapter;
    String project_id;
    String document_id;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        tab2 = inflater.inflate(R.layout.fragment_project_tab2, container, false);

        ListView listView = (ListView) tab2.findViewById(R.id.documentList);

        adapter = new DocumentViewAdapter();

        listView.setAdapter(adapter);

        String url = "http://uoshue.dothome.co.kr/loadDocument.php?";
        ContentValues values = new ContentValues();
        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);
        project_id = prefs.getString("project_id",null);
        values.put("project_id",project_id);
        NetworkTask6 networkTask6 = new NetworkTask6(url, values);
        networkTask6.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                document_id = String.valueOf(((DocumentViewItem)adapterView.getAdapter().getItem(i)).getDocumentId());
                Log.d("문서",project_id+"/"+document_id);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(tab2.getContext(), DocumentDetailActivity.class);
                        intent.putExtra("project_id",project_id);
                        intent.putExtra("document_id",document_id);
                        startActivity(intent);

                    }
	            }, 1000);



            }
        });

        return tab2;
    }

    public class NetworkTask6 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask6(String url, ContentValues values) {

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
                if (result != null) {
                    JSONArray jarr = new JSONArray(result);

                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = (JSONObject) jarr.get(i);

                        String memlist = "";
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
                        // 접근한 회원테이블에 id가 있는지 있으면 그 튜플 반환
                        // 튜플로부터 데이터를 뽑아 어댑터에 추가
                        adapter.addItem(json.getInt("id"),json.getString("name"), json.getString("created"), json.getString("updated"), memlist);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();

        }
    }
}
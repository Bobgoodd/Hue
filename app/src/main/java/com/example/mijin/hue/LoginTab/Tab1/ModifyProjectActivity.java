package com.example.mijin.hue.LoginTab.Tab1;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mijin.hue.LoginTab.LoginTabActivity;
import com.example.mijin.hue.LoginTab.Tab3.FriendViewAdapter;
import com.example.mijin.hue.LoginTab.Tab3.FriendViewItem;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mijin on 2017-11-15.
 */

public class ModifyProjectActivity extends AppCompatActivity{
    ArrayList<FriendViewItem> mem = new ArrayList<FriendViewItem>();
    ListView listView;
    FriendViewAdapter adapter;
    EditText friendId;
    String url;
    ContentValues values;
    NetworkTask2 networkTask2;
    CheckBox ch;
    SharedPreferences prefs;
    Intent intent2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_tab3);
        intent2 = getIntent();


        prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        adapter = new FriendViewAdapter();



        listView = (ListView) findViewById(R.id.friendList);
        listView.setAdapter(adapter);


        adapter.setFlag(true);


        url =  "http://uoshue.dothome.co.kr/loadFriendInProject.php?";
        values = new ContentValues();
        values.put("usr_id",prefs.getString("id",null));
        values.put("project_id",intent2.getStringExtra("project_id"));
        networkTask2 = new NetworkTask2(url,values);
        networkTask2.execute();


        Button search = (Button) findViewById(R.id.search);
        search.setText("추가");
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(mem.size()==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("멤버를 선택해주세요.");
                    builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else {
                    url =  "http://uoshue.dothome.co.kr/addProjectMember.php?";
                    values = new ContentValues();

                    values.put("project_id",intent2.getStringExtra("project_id"));
                    for(int k=0;k<mem.size();k++) {
                        values.put("usr_id" + k, mem.get(k).getId());
                    }
                    values.put("k",mem.size());
                    networkTask2 = new NetworkTask2(url, values);
                    networkTask2.execute();
                    Intent intent = new Intent(getApplicationContext(), LoginTabActivity.class);
                    intent.putExtra("project_id",intent2.getStringExtra("project_id"));
                    intent.putParcelableArrayListExtra("mem", mem);
                    startActivityForResult(intent, 0);
                }
            }
        });

        com.melnykov.fab.FloatingActionButton fabb = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fabb);
        fabb.setVisibility(View.INVISIBLE);


        friendId = (EditText) findViewById(R.id.friendId);

        friendId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listView.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ch = (CheckBox) view.findViewById(R.id.ch);

                if(mem.contains((FriendViewItem)adapterView.getAdapter().getItem(i))) {
                    mem.remove((FriendViewItem) adapterView.getAdapter().getItem(i));
                    mem.removeAll(Collections.singleton(null));
                    Toast.makeText(getApplicationContext(), ((FriendViewItem) adapterView.getAdapter().getItem(i)).getId()+" remove", Toast.LENGTH_LONG).show();
                    ch.setChecked(false);
                }else{
                    mem.add((FriendViewItem)adapterView.getAdapter().getItem(i));
                    Toast.makeText(getApplicationContext(), ((FriendViewItem) adapterView.getAdapter().getItem(i)).getId()+" add", Toast.LENGTH_LONG).show();
                    ch.setChecked(true);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&requestCode==RESULT_OK)
            finish();
    }

    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask2(String url, ContentValues values) {

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
                        adapter.addItem(R.drawable.man, json.getString("id"), json.getString("name"), json.getString("email"), "010-3315-4444");

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();

        }
    }
}


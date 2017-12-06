package com.example.mijin.hue.LoginTab.Tab3;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mijin.hue.MainActivity;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.INVISIBLE;

/**
 * Created by mijin on 2017-10-22.
 */

public class NewFriendActivity extends AppCompatActivity{

    ListView listView;
    FriendViewAdapter adapter1;
    EditText friendId;
    String url;
    ContentValues values;
    NetworkTask networkTask;
    String id;
    SharedPreferences prefs;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK)
            finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newfriend);

        friendId = (EditText) findViewById(R.id.friendId);


        Button search;
        com.melnykov.fab.FloatingActionButton floating;

        floating = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fabb);
        floating.setVisibility(INVISIBLE);
        prefs = getSharedPreferences("PrefName",MODE_PRIVATE);

        adapter1 = new FriendViewAdapter();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listView = (ListView) findViewById(R.id.friendList);
        listView.setAdapter(adapter1);

        search = (Button) findViewById(R.id.search);


        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                adapter1.clear();
                url = "http://uoshue.dothome.co.kr/findId.php?";
                values = new ContentValues();
                values.put("usr_id",prefs.getString("id",null));
                values.put("memId", friendId.getText().toString());

                networkTask = new NetworkTask(url, values);
                networkTask.execute();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FriendViewItem item = (FriendViewItem) adapterView.getAdapter().getItem(i);
                Toast.makeText(getApplicationContext(), item.getId().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //Intent intent = new Intent(view.getContext(), LoginTabFragment3.class);
                //intent.putExtra("newactivity", true);
                intent.putExtra("new", item); // 한명씩 추가
                url = "http://uoshue.dothome.co.kr/addFriend.php?";
                values = new ContentValues();

                values.put("usr_id",prefs.getString("id",null));
                values.put("friend_id",id);
                networkTask = new NetworkTask(url,values);
                networkTask.execute();
                startActivityForResult(intent, 1);
            }
        });




    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

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
                        adapter1.addItem(R.drawable.man, json.getString("id"), json.getString("name"), json.getString("email"), "010-3315-4444");
                        id = json.getString("id");

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter1.notifyDataSetChanged();

        }
    }

}



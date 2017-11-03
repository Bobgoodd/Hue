package com.example.mijin.hue.LoginTab.Tab3;

import android.content.ContentValues;
import android.content.Intent;
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

import com.example.mijin.hue.LoginTab.LoginTabActivity;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;
import com.google.gson.Gson;

import static android.view.View.INVISIBLE;

/**
 * Created by mijin on 2017-10-22.
 */

public class NewFriendActivity extends AppCompatActivity{

    ListView listView;
    FriendViewAdapter adapter1;
    EditText friendId;
    String url;
    ContentValues values = new ContentValues();

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
        friendId.setFocusable(false);

        Button search;
        com.melnykov.fab.FloatingActionButton floating;

        floating = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fabb);
        floating.setVisibility(INVISIBLE);


        adapter1 = new FriendViewAdapter();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listView = (ListView) findViewById(R.id.friendList);
        listView.setAdapter(adapter1);

        adapter1.addItem(R.drawable.man, "add", "김동수", "dd@naver.com", "010-3315-4444");
        search = (Button) findViewById(R.id.search);


        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*String id = friendId.getText().toString();
                FriendViewItem friendViewItem;
                int fprofile, j;
                String fid, fname, femail, fphone;



                if (id != null){
                    adapter1.clear();


                    String body = "id="+id;
                    new HttpTask().execute(body);

                }
*/
/*
                url = "http://uoshue.dothome.co.kr/findId.php";

                values.put("memId", friendId.getText().toString());

                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();
 */           }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FriendViewItem item = (FriendViewItem) adapterView.getAdapter().getItem(i);
                Toast.makeText(getApplicationContext(), item.getId().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoginTabActivity.class);
                //Intent intent = new Intent(view.getContext(), LoginTabFragment3.class);
                //intent.putExtra("newactivity", true);
                intent.putExtra("new", item); // 한명씩 추가
                startActivityForResult(intent, 1);
            }
        });




    }

    public class NetworkTask extends AsyncTask<Void, Void, FriendViewItem[]> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected FriendViewItem[] doInBackground(Void... params) {

            FriendViewItem[] friendViewItems;
            String json;

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            json = result.toString();

            friendViewItems = new Gson().fromJson(json, FriendViewItem[].class);

            return friendViewItems;
        }

        @Override
        protected void onPostExecute(FriendViewItem[] friendViewItems) {
            super.onPostExecute(friendViewItems);
            for(FriendViewItem item : friendViewItems){
                // 접근한 회원테이블에 id가 있는지 있으면 그 튜플 반환
                // 튜플로부터 데이터를 뽑아 어댑터에 추가
                adapter1.addItem(item.getProfile(), item.getId(), item.getName(), item.getEmail(), item.getPhone());


            }
            adapter1.notifyDataSetChanged();
        }
    }

}



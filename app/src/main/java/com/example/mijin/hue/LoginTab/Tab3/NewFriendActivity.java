package com.example.mijin.hue.LoginTab.Tab3;

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

import com.example.mijin.hue.LoginTab.LoginTabActivity;
import com.example.mijin.hue.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mijin on 2017-10-22.
 */

public class NewFriendActivity extends AppCompatActivity{

    ListView listView;
    FriendViewAdapter adapter1;
    EditText friendId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newfriend);

        friendId = (EditText) findViewById(R.id.friendId);

        Button search;
        com.melnykov.fab.FloatingActionButton floating;

        floating = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fabb);
        floating.setVisibility(View.INVISIBLE);

        adapter1 = new FriendViewAdapter();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listView = (ListView) findViewById(R.id.friendList);
        listView.setAdapter(adapter1);
        adapter1.addItem(R.drawable.man, "add", "김동수", "dd@naver.com", "010-3315-4444");

        search = (Button) findViewById(R.id.search);

        /* php 코드 완성되면 활성화
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = friendId.getText().toString();
                FriendViewItem friendViewItem;
                int fprofile, j;
                String fid, fname, femail, fphone;



                if (id != null){
                    adapter1.clear();


                    String body = "id="+id;
                    new HttpTask().execute(body);

                }

            }
        });
        */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FriendViewItem item = (FriendViewItem) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getApplicationContext(), LoginTabActivity.class);
                //Intent intent = new Intent(view.getContext(), LoginTabFragment3.class);
                intent.putExtra("newactivity", true);
                intent.putExtra("new", item); // 한명씩 추가
                startActivity(intent);
            }
        });




    }


    class HttpTask extends AsyncTask<String, Void, FriendViewItem[]> {
        private String urlstr ="NewFriend.php"; // 서버주소
        @Override
        protected FriendViewItem[] doInBackground(String... voids) {

            FriendViewItem[] friendViewItems;
            String json;

            try{// php를 이용해서 데이터베이스 접근
                String body = voids.toString();
                URL url = new URL(urlstr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                // POST방식으로 요청한다.( 기본값은 GET )
                con.setRequestMethod("POST");
                // InputStream으로 서버로 부터 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의한다.
                con.setDoInput(true);
                // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션을 정의한다.
                con.setDoOutput(true);
                // 요청 헤더를 정의한다.( 원래 Content-Length값을 넘겨주어야하는데 넘겨주지 않아도 되는것이 이상하다. )
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // 새로운 OutputStream에 요청할 OutputStream을 넣는다.
                OutputStream os = con.getOutputStream();
                // 그리고 write메소드로 메시지로 작성된 파라미터정보를 바이트단위로 "EUC-KR"로 인코딩해서 요청한다.
                // 여기서 중요한 점은 "UTF-8"로 해도 되는데 한글일 경우는 "EUC-KR"로 인코딩해야만 한글이 제대로 전달된다.
                os.write( body.getBytes("euc-kr") );
                // 그리고 스트림의 버퍼를 비워준다.
                os.flush();
                // 스트림을 닫는다.
                os.close();

                // 응답받은 메시지의 길이만큼 버퍼를 생성하여 읽어들이고, "EUC-KR"로 디코딩해서 읽어들인다.
                BufferedReader br = new BufferedReader( new InputStreamReader( con.getInputStream(), "EUC-KR" ), con.getContentLength() );

                json = br.toString();
                // 스트림을 닫는다.
                br.close();


                //JSON 형식 {  {'profile' : '', 'id' : '', 'name' : '', 'email' : '', 'phone' : ''}, {}, {}}
                friendViewItems = new Gson().fromJson(json, FriendViewItem[].class);

                return friendViewItems;

            }catch(Exception e){
                e.toString();
            }

            return null;
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



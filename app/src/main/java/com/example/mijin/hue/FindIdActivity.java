package com.example.mijin.hue;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mijin on 2017-11-15.
 */

public class FindIdActivity extends Activity {
    EditText email;
    Button find;
    TextView show;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        email = (EditText) findViewById(R.id.inputEmail);

        show = (TextView) findViewById(R.id.showId);

        find = (Button) findViewById(R.id.findInputId);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().equals("")){
                    String url = "http://uoshue.dothome.co.kr/findUserId.php?";
                    ContentValues values = new ContentValues();
                    values.put("email",email.getText().toString());

                    NetworkTask8 networkTask8 = new NetworkTask8(url, values);
                    networkTask8.execute();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                    builder.setMessage("이메일을 입력해주세요.");
                    builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }

            }
        });

    }

    public class NetworkTask8 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask8(String url, ContentValues values) {

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


            if(result!=null) {
                if(!result.equals("no")){

                    String showId = result;
                    show.setText("회원님의 아이디는 " + showId + "입니다.");
                    show.setVisibility(View.VISIBLE);

                }else{

                    show.setText("정보를 찾을 수 없습니다.");
                    show.setVisibility(View.VISIBLE);
                }


            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                builder.setMessage("다시 입력해주세요.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }

        }
    }
}

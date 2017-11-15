package com.example.mijin.hue;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mijin on 2017-11-15.
 */

public class FindPwActivity extends Activity {
    EditText id, email;
    Button find;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        id = (EditText) findViewById(R.id.inputId);
        email = (EditText) findViewById(R.id.inputEmail1);


        find = (Button) findViewById(R.id.findInputPw);

        find.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(!id.getText().toString().equals("")&&!email.getText().toString().equals("")) {
                    String url = "http://uoshue.dothome.co.kr/findUserPw.php?";
                    ContentValues values = new ContentValues();
                    values.put("id",id.getText().toString());
                    values.put("email", email.getText().toString());

                    NetworkTask9 networkTask9 = new NetworkTask9(url, values);
                    networkTask9.execute();


                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                    builder.setMessage("정보를 입력해주세요.");
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

    public class NetworkTask9 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask9(String url, ContentValues values) {

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
                    if(!result.equals("[]")){
                    Intent intent = new Intent(getApplicationContext(), UpdatePwActivity.class);
                    intent.putExtra("id",id.getText().toString());
                    startActivity(intent);

                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                        builder.setMessage("정보를 찾을 수 없습니다.");
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
}

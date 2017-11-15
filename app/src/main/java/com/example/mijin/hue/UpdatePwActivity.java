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

public class UpdatePwActivity extends Activity {

    Intent intent;
    EditText newpw, newconfpw;
    Button update;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pw);

        intent = getIntent();

        newpw = (EditText) findViewById(R.id.inputNewPw1);
        newconfpw = (EditText) findViewById(R.id.inputNewPw2);

        update = (Button) findViewById(R.id.updatePw);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newpw.getText().toString().equals("")&&!newconfpw.getText().toString().equals("")){
                    if(newpw.getText().toString().equals(newconfpw.getText().toString())){

                        String url = "http://uoshue.dothome.co.kr/updatePw.php?";
                        ContentValues values = new ContentValues();
                        values.put("id", intent.getStringExtra("id"));
                        values.put("new", newpw.getText().toString());

                        NetworkTask10 networkTask10 = new NetworkTask10(url, values);
                        networkTask10.execute();

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePwActivity.this);
                        builder.setMessage("새 비밀번호가 불일치합니다.");
                        builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePwActivity.this);
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

    public class NetworkTask10 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask10(String url, ContentValues values) {

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
                if(result.equals("success")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePwActivity.this);
                    builder.setMessage("변경되었습니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                    Intent intent = new Intent(UpdatePwActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else if(result.equals("err")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePwActivity.this);
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
}

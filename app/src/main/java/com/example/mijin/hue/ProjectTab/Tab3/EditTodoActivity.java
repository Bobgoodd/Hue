package com.example.mijin.hue.ProjectTab.Tab3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

/**
 * Created by mijin on 2017-11-27.
 */

public class EditTodoActivity extends AppCompatActivity{
    EditText item, progress;
    int id;
    boolean is;
    String url, usr_id, project_id;
    ContentValues values;
    NetworkTask33 networkTask33;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_todo);

        SharedPreferences prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        usr_id = prefs.getString("id",null);
        project_id = prefs.getString("project_id",null);

        Button save = (Button) findViewById(R.id.save2);
        Button cancel = (Button) findViewById(R.id.cancel2);

        item = (EditText) findViewById(R.id.item);
        progress = (EditText) findViewById(R.id.progress);

        Intent intent = getIntent();
        is=intent.getBooleanExtra("isProjectTab3",false);
        if(is==false) {
            String witem = intent.getStringExtra("item");
            int wprogress = intent.getIntExtra("progress", 0);
            id = intent.getIntExtra("id", 0);
            item.setText(witem);
            progress.setText(String.valueOf(wprogress));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("project_id",project_id);
                if(!item.getText().toString().equals("")||!progress.getText().toString().equals("")){
                    int current = Integer.parseInt(progress.getText().toString());
                    if(current>=0&&current<=100) {
                        values = new ContentValues();
                        values.put("usr_id",usr_id);
                        values.put("project_id",project_id);
                        if(is==true){
                            values.put("item",item.getText().toString());
                            values.put("progress",progress.getText().toString());
                            url = "http://uoshue.dothome.co.kr/addTask.php?";
                            networkTask33 = new NetworkTask33(url,values);
                            networkTask33.execute();
                            //intent.putExtra("isProjectTab3",is);
                            //startActivityForResult(intent, 10);
                        }else {
                            values.put("id",id);
                            values.put("item",item.getText().toString());
                            values.put("progress",progress.getText().toString());
                            url = "http://uoshue.dothome.co.kr/updateTask.php?";
                            networkTask33 = new NetworkTask33(url,values);
                            networkTask33.execute();
                            //intent.putExtra("isEdit", true);
                            //startActivityForResult(intent, 10);
                        }


                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.MyAlertDialog);
                        builder.setMessage("가능한 범위가 아닙니다.");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.MyAlertDialog);
                    builder.setMessage("내용을 입력해주세요.");
                   builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    public class NetworkTask33 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask33(String url, ContentValues values) {

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

            Intent returnintent = new Intent();
            setResult(Activity.RESULT_OK,returnintent);
            EditTodoActivity.this.finish();


        }
    }


}

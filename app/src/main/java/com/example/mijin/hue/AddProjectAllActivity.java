package com.example.mijin.hue;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mijin.hue.LoginTab.Tab1.AddProjectActivity;
import com.example.mijin.hue.LoginTab.Tab3.FriendViewItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by mijin on 2017-12-06.
 */

public class AddProjectAllActivity extends AppCompatActivity{

    MaterialNumberPicker startYear, startMonth, startDay, endYear, endMonth, endDay;
    TextView startText, endText;
    EditText event;

    FriendViewItemAdapter2 adapter1;
    Button addFriend;
    ScrollView scroll;


    String url;
    ContentValues values;
    NetworkTask2 networkTask2;

    SharedPreferences prefs;

    ArrayList<FriendViewItem> items;
    String sTime, eTime;
    int t, d, m, y, t2, d2, m2, y2;
    String id;

    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_project_all);

        prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        id = prefs.getString("id",null);


        startText = (TextView) findViewById(R.id.startText);
        endText = (TextView) findViewById(R.id.endText);

        startYear = (MaterialNumberPicker) findViewById(R.id.startYear);
        startMonth = (MaterialNumberPicker) findViewById(R.id.startMonth);
        startDay = (MaterialNumberPicker) findViewById(R.id.startDay);

        startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일");

        endYear = (MaterialNumberPicker) findViewById(R.id.endYear);
        endMonth = (MaterialNumberPicker) findViewById(R.id.endMonth);
        endDay = (MaterialNumberPicker) findViewById(R.id.endDay);

        endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일");

        event = (EditText) findViewById(R.id.event);

        startYear.setValue(cal.get(Calendar.YEAR));
        startMonth.setValue(cal.get(Calendar.MONTH)+1);
        calendar = new GregorianCalendar(startYear.getValue(),startMonth.getValue()-1,1);
        startDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        startDay.setValue(cal.get(Calendar.DATE));



        startYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startYear.setValue(newVal);
                calendar = new GregorianCalendar(startYear.getValue(),startMonth.getValue()-1,1);
                startDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일");
            }
        });

        startMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startMonth.setValue(newVal);
                calendar = new GregorianCalendar(startYear.getValue(),startMonth.getValue()-1,1);
                startDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일");
            }
        });

        startDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startDay.setValue(newVal);
                startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일");
            }
        });


        endYear.setValue(cal.get(Calendar.YEAR));
        endMonth.setValue(cal.get(Calendar.MONTH)+1);
        calendar = new GregorianCalendar(endYear.getValue(),endMonth.getValue()-1,1);
        endDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDay.setValue(cal.get(Calendar.DATE));


        endYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endYear.setValue(newVal);
                calendar = new GregorianCalendar(endYear.getValue(),endMonth.getValue()-1,1);
                endDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일");
            }
        });

        endMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endMonth.setValue(newVal);
                calendar = new GregorianCalendar(endYear.getValue(),endMonth.getValue()-1,1);
                endDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일");
            }
        });

        endDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endDay.setValue(newVal);
                endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일");
            }
        });




        scroll = (ScrollView) findViewById(R.id.scroll);

        addFriend = (Button) findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProjectAllActivity.this, AddProjectActivity.class);
                startActivityForResult(intent,50);
            }
        });



        ListView listView = (ListView) findViewById(R.id.friendList);

        adapter1 = new FriendViewItemAdapter2();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listView.setAdapter(adapter1);


        Button cancel2 = (Button) findViewById(R.id.cancel2);
        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button save2 = (Button) findViewById(R.id.save2);
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (items!=null&&items.size() != 0 && !event.getText().toString().equals("")) {
                    d = startDay.getValue();
                    m = startMonth.getValue();
                    y = startYear.getValue();
                    //t = startTime.getNewCurrentTime().getHourOfDay();

                    sTime = y + "-" + m + "-" + d;

                    d2 = endDay.getValue();
                    m2 = endMonth.getValue();
                    y2 = endYear.getValue();


                    values = new ContentValues();

                    if (y2 < y) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddProjectAllActivity.this, R.style.MyAlertDialog);
                        builder.setMessage("일정을 다시 확인해주세요.");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    } else if (y2 == y) {
                        if (m2 < m) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AddProjectAllActivity.this, R.style.MyAlertDialog);
                            builder.setMessage("일정을 다시 확인해주세요.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        } else if (m2 == m) {
                            if (d2 < d) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddProjectAllActivity.this, R.style.MyAlertDialog);
                                builder.setMessage("일정을 다시 확인해주세요.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.show();
                            } else if (d2 >= d) {

                                eTime = y2 + "-" + m2 + "-" + d2;

                            }

                        }
                    } else {//성공

                        eTime = y2 + "-" + m2 + "-" + d2;


                    }

                    url = "http://uoshue.dothome.co.kr/addProject.php?";


                    values.put("director_id", id);
                    values.put("start", sTime);
                    values.put("end", eTime);
                    values.put("name", event.getText().toString());
                    for (int k = 0; k < items.size(); k++) {
                        values.put("usr_id" + k, items.get(k).getId());
                    }
                    values.put("k", items.size());

                    networkTask2 = new NetworkTask2(url, values);
                    networkTask2.execute();


                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK,returnIntent);

                    finish();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProjectAllActivity.this, R.style.MyAlertDialog);
                    builder.setMessage("정보를 모두 입력해주세요.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });
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


        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  if(requestCode==30&&resultCode==RESULT_OK)

        if(data.getParcelableArrayListExtra("mem")!=null){

            addFriend.setVisibility(View.GONE);

            items = data.getParcelableArrayListExtra("mem");

            if (items != null) {
                for (FriendViewItem item : items) {
                    Log.d("받은정보", item.getId());
                    adapter1.addItem(R.drawable.man, item.getId(), item.getName(), item.getEmail(), item.getPhone());
                }
            }

            adapter1.notifyDataSetChanged();

            scroll.setVisibility(View.VISIBLE);



        }
    }



}
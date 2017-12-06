package com.example.mijin.hue.Day;

/**
 * Created by mijin on 2017-11-26.
 */

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.example.mijin.hue.MainActivity;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mijin on 2017-11-24.
 */

public class GroupSettingScheduleActivity extends AppCompatActivity {

    DatePicker startDate, endDate;
    //FrameLayout startDate, endDate;
    //ClockView startTime, endTime;
    NumberPicker startTime, endTime;
    DatePicker start, end;
    String url, project_id, id, sTime, eTime;
    ContentValues values;
    int t, d, m, y, t2, d2, m2, y2;
    NetworkTask30 networkTask30;
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();

    Intent in;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting_schedule);
        in = getIntent();

        SharedPreferences prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        project_id = prefs.getString("project_id",null);
        id = prefs.getString("id",null);

        startDate = (DatePicker) findViewById(R.id.startDate);
        endDate = (DatePicker) findViewById(R.id.endDate);

        //startDate = (FrameLayout) findViewById(R.id.startDate);
        //endDate = (FrameLayout) findViewById(R.id.endDate);

        //start = new DatePicker(startDate,null);
        //end = new DatePicker(endDate,null);


        //startDate = start;
        //endDate = end;




        startTime = (NumberPicker) findViewById(R.id.startTime);
        endTime = (NumberPicker) findViewById(R.id.endTime);


        startTime.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //t=i1;
                numberPicker.setValue(i1);
            }
        });
        endTime.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //t2=i1;
                numberPicker.setValue(i1);
            }
        });
        startTime.setMinValue(1);
        startTime.setMaxValue(24);

        endTime.setMinValue(1);
        endTime.setMaxValue(24);


/*
        startTime = (ClockView) findViewById(R.id.startTime);
        endTime = (ClockView) findViewById(R.id.endTime);


        DateTime minTime = new DateTime(2014, 4, 24, 0, 0);

        DateTime maxTime = new DateTime(2014, 4, 25, 0, 0);


        sMinDepartTimeClockView = startTime;

        startTime.setBounds(minTime, maxTime, false);

        startTime.setNewCurrentTime(new DateTime(2014, 4, 24, 0, 0));



        sMaxDepartTimeClockView = endTime;

        endTime.setBounds(minTime, maxTime, false);

        endTime.setNewCurrentTime(new DateTime(2014, 4, 25, 0, 0));

        changeClockTimeForTests(DateTime.now(),false);

*/

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                d = start.getDayOfMonth();
                m = start.getMonth()+1;
                y = start.getYear();
                //t = startTime.getNewCurrentTime().getHourOfDay();
                t=startTime.getValue();
                sTime = y+"-"+m+"-"+d+" "+t+":00:00";

                d2 = end.getDayOfMonth();
                m2 = end.getMonth()+1;
                y2 = end.getYear();

                //t2 = endTime.getNewCurrentTime().getHourOfDay();
                t2=endTime.getValue();

                url = "http://uoshue.dothome.co.kr/addGroupSchedule.php?";
                values = new ContentValues();
                values.put("id",id);
                values.put("project_id", project_id);
                values.put("start",sTime);

                if(y2<y){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity.this,R.style.MyAlertDialog);
                    builder.setMessage("일정을 다시 확인해주세요.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }else if(y2==y){
                    if(m2<m){
                        AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity.this,R.style.MyAlertDialog);
                        builder.setMessage("일정을 다시 확인해주세요.");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }else if(m2==m){
                        if(d2<d){
                            AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity.this,R.style.MyAlertDialog);
                            builder.setMessage("일정을 다시 확인해주세요.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }else if(d2==d){
                            if(t2<=t||t2==24){
                                AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity.this,R.style.MyAlertDialog);
                                builder.setMessage("일정을 다시 확인해주세요.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.show();
                            }else{

                                eTime = y2 + "-" + m2 + "-" + d2 + " "+ (t2-1) +":59:59";


                                values.put("end",eTime);
                                networkTask30 = new NetworkTask30(url, values);
                                networkTask30.execute();

                            }
                        }else{//성공
                            if(t2==24) {
                                eTime = y2 + "-" + m2 + "-" + d2 + " " +"23:59:59";
                                try {
                                    cal.setTime(dformat.parse(eTime));
                                    cal.add(Calendar.DATE,-1);
                                    eTime = dformat.format(cal.getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                eTime = y2 + "-" + m2 + "-" + d2 + " "+ (t2-1) +":59:59";
                            }

                            values.put("end",eTime);
                            networkTask30 = new NetworkTask30(url, values);
                            networkTask30.execute();

                        }
                    }else{//성공
                        if(t2==24) {
                            eTime = y2 + "-" + m2 + "-" + d2 + " " +"23:59:59";
                            try {
                                cal.setTime(dformat.parse(eTime));
                                cal.add(Calendar.DATE,-1);
                                eTime = dformat.format(cal.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else{
                            eTime = y2 + "-" + m2 + "-" + d2 + " "+ (t2-1) +":59:59";
                        }

                        values.put("end",eTime);
                        networkTask30 = new NetworkTask30(url, values);
                        networkTask30.execute();

                    }
                }else{//성공
                    if(t2==24) {
                        eTime = y2 + "-" + m2 + "-" + d2 + " " +"23:59:59";
                        try {
                            cal.setTime(dformat.parse(eTime));
                            cal.add(Calendar.DATE,-1);
                            eTime = dformat.format(cal.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else{
                        eTime = y2 + "-" + m2 + "-" + d2 + " "+ (t2-1) +":59:59";
                    }

                    values.put("end",eTime);
                    networkTask30 = new NetworkTask30(url, values);
                    networkTask30.execute();

                }




            }
        });

    }




    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        startDate.updateDate(year,monthOfYear,dayOfMonth);
        endDate.updateDate(year,monthOfYear,dayOfMonth);
    }


    public class NetworkTask30 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask30(String url, ContentValues values) {

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

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("setting","ok");
            if(in!=null) intent.putExtra("date",in.getStringExtra("date"));
            startActivityForResult(intent, 2);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK)
            finish();
    }
}
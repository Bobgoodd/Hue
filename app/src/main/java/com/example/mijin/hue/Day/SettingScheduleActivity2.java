package com.example.mijin.hue.Day;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.mijin.hue.BroadcastD;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by mijin on 2017-11-29.
 */

public class SettingScheduleActivity2 extends AppCompatActivity {


    MaterialNumberPicker startYear, startMonth, startDay, startHour, endYear, endMonth, endDay, endHour;
    TextView startText, endText;
    EditText event;
    LinearLayout eventName;
    String url, id, sTime, eTime, item;
    ContentValues values;
    int t, d, m, y, t2, d2, m2, y2;
    NetworkTask30 networkTask30;
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    Calendar calendar;

    Intent in;





    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting_schedule2);

        in = getIntent();

        SharedPreferences prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        id = prefs.getString("id",null);

        startText = (TextView) findViewById(R.id.startText);
        endText = (TextView) findViewById(R.id.endText);

        startYear = (MaterialNumberPicker) findViewById(R.id.startYear);
        startMonth = (MaterialNumberPicker) findViewById(R.id.startMonth);
        startDay = (MaterialNumberPicker) findViewById(R.id.startDay);
        startHour = (MaterialNumberPicker) findViewById(R.id.startHour);

        startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일 "+startHour.getValue()+"시");

        endYear = (MaterialNumberPicker) findViewById(R.id.endYear);
        endMonth = (MaterialNumberPicker) findViewById(R.id.endMonth);
        endDay = (MaterialNumberPicker) findViewById(R.id.endDay);
        endHour = (MaterialNumberPicker) findViewById(R.id.endHour);

        endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일 "+endHour.getValue()+"시");


        event = (EditText) findViewById(R.id.event);

        eventName = (LinearLayout) findViewById(R.id.eventName);
        eventName.setVisibility(View.VISIBLE);

        startYear.setValue(cal.get(Calendar.YEAR));
        startMonth.setValue(cal.get(Calendar.MONTH)+1);
        calendar = new GregorianCalendar(startYear.getValue(),startMonth.getValue()-1,1);
        startDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        startDay.setValue(cal.get(Calendar.DATE));
        startHour.setValue(1);


        startYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startYear.setValue(newVal);
                calendar = new GregorianCalendar(startYear.getValue(),startMonth.getValue()-1,1);
                startDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일 "+startHour.getValue()+"시");
            }
        });

        startMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startMonth.setValue(newVal);
                calendar = new GregorianCalendar(startYear.getValue(),startMonth.getValue()-1,1);
                startDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일 "+startHour.getValue()+"시");
            }
        });

        startDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startDay.setValue(newVal);
                startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일 "+startHour.getValue()+"시");
            }
        });

        startHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startHour.setValue(newVal);
                startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일 "+startHour.getValue()+"시");
            }
        });

        endYear.setValue(cal.get(Calendar.YEAR));
        endMonth.setValue(cal.get(Calendar.MONTH)+1);
        calendar = new GregorianCalendar(endYear.getValue(),endMonth.getValue()-1,1);
        endDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDay.setValue(cal.get(Calendar.DATE));
        endHour.setValue(1);

        endYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endYear.setValue(newVal);
                calendar = new GregorianCalendar(endYear.getValue(),endMonth.getValue()-1,1);
                endDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일 "+endHour.getValue()+"시");
            }
        });

        endMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endMonth.setValue(newVal);
                calendar = new GregorianCalendar(endYear.getValue(),endMonth.getValue()-1,1);
                endDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일 "+endHour.getValue()+"시");
            }
        });

        endDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endDay.setValue(newVal);
                endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일 "+endHour.getValue()+"시");
            }
        });

        endHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endHour.setValue(newVal);
                endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일 "+endHour.getValue()+"시");
            }
        });
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

                Log.d("그룹","추가하는데");
                item = event.getText().toString();
                if((item.length()<8)||(item.length()>=8&&!item.substring(0,8).equals("project#"))) {
                    d = startDay.getValue();
                    m = startMonth.getValue();
                    y = startYear.getValue();
                    //t = startTime.getNewCurrentTime().getHourOfDay();
                    t = startHour.getValue();
                    sTime = y + "-" + m + "-" + d + " " + t + ":00:00";

                    d2 = endDay.getValue();
                    m2 = endMonth.getValue();
                    y2 = endYear.getValue();

                    //t2 = endTime.getNewCurrentTime().getHourOfDay();
                    t2 = endHour.getValue();



                    url = "http://uoshue.dothome.co.kr/addPersonalSchedule.php?";
                    values = new ContentValues();
                    values.put("id", id);
                    values.put("start", sTime);
                    values.put("item", item);

                    if (y2 < y) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingScheduleActivity2.this, R.style.MyAlertDialog);
                        builder.setMessage("일정을 다시 확인해주세요.");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    } else if (y2 == y) {
                        if (m2 < m) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SettingScheduleActivity2.this, R.style.MyAlertDialog);
                            builder.setMessage("일정을 다시 확인해주세요.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        } else if (m2 == m) {
                            if (d2 < d) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SettingScheduleActivity2.this, R.style.MyAlertDialog);
                                builder.setMessage("일정을 다시 확인해주세요.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.show();
                            } else if (d2 == d) {
                                if (t2 <= t) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingScheduleActivity2.this, R.style.MyAlertDialog);
                                    builder.setMessage("일정을 다시 확인해주세요.");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    builder.show();
                                } else {
                                    if(t2==0){
                                        eTime = y2 + "-" + m2 + "-" + d2 + " " + "23:59:59";
                                        try {
                                            cal.setTime(dformat.parse(eTime));
                                            cal.add(Calendar.DATE, -1);
                                            eTime = dformat.format(cal.getTime());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                        eTime = y2 + "-" + m2 + "-" + d2 + " " + (t2 - 1) + ":59:59";
                                    }


                                    values.put("end", eTime);
                                    networkTask30 = new NetworkTask30(url, values);
                                    networkTask30.execute();

                                }
                            } else {//성공
                                if (t2 == 0) {
                                    eTime = y2 + "-" + m2 + "-" + d2 + " " + "23:59:59";
                                    try {
                                        cal.setTime(dformat.parse(eTime));
                                        cal.add(Calendar.DATE, -1);
                                        eTime = dformat.format(cal.getTime());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    eTime = y2 + "-" + m2 + "-" + d2 + " " + (t2 - 1) + ":59:59";
                                }

                                values.put("end", eTime);
                                networkTask30 = new NetworkTask30(url, values);
                                networkTask30.execute();

                            }
                        } else {//성공
                            if (t2 == 0) {
                                eTime = y2 + "-" + m2 + "-" + d2 + " " + "23:59:59";
                                try {
                                    cal.setTime(dformat.parse(eTime));
                                    cal.add(Calendar.DATE, -1);
                                    eTime = dformat.format(cal.getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                eTime = y2 + "-" + m2 + "-" + d2 + " " + (t2 - 1) + ":59:59";
                            }

                            values.put("end", eTime);
                            networkTask30 = new NetworkTask30(url, values);
                            networkTask30.execute();

                        }
                    } else {//성공
                        if (t2 == 0) {
                            eTime = y2 + "-" + m2 + "-" + d2 + " " + "23:59:59";
                            try {
                                cal.setTime(dformat.parse(eTime));
                                cal.add(Calendar.DATE, -1);
                                eTime = dformat.format(cal.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            eTime = y2 + "-" + m2 + "-" + d2 + " " + (t2 - 1) + ":59:59";
                        }

                        values.put("end", eTime);
                        networkTask30 = new NetworkTask30(url, values);
                        networkTask30.execute();

                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingScheduleActivity2.this, R.style.MyAlertDialog);
                    builder.setMessage("이벤트명은 'project#'으로 시작할 수 없습니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }




            }
        });

        CheckBox alarm = (CheckBox) findViewById(R.id.alarm);
        alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){

                    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(SettingScheduleActivity2.this, BroadcastD.class);
                    intent.putExtra("isSetting",true);
                    intent.putExtra("content",id+"님의 개인일정");

                    PendingIntent sender = PendingIntent.getBroadcast(SettingScheduleActivity2.this, 0, intent,  0);

                    Calendar calendar = Calendar.getInstance();
                    //알람시간 calendar에 set해주기

                    //calendar.set(y, m, d, t, 0, 0);
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 4, 25, 0);



                    //알람 예약
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

                }else{
                    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(SettingScheduleActivity2.this, BroadcastD.class);
                    intent.putExtra("isSetting",true);

                    PendingIntent sender = PendingIntent.getBroadcast(SettingScheduleActivity2.this, 0, intent,  0);
                    am.cancel(sender);
                }
            }
        });

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
            if(result!=null) {
                if(result.equals("err1")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingScheduleActivity2.this,R.style.MyAlertDialog);
                    builder.setMessage("프로젝트 일정과 겹칩니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else if(result.equals("success")) {
                    /*
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("setting", "ok");
                    if (in != null) intent.putExtra("date", in.getStringExtra("date"));
                    startActivityForResult(intent, 2);
                    */
                    Intent returnintent = new Intent();
                    setResult(Activity.RESULT_OK,returnintent);
                    SettingScheduleActivity2.this.finish();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK)
            finish();
    }

}

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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.mijin.hue.BroadcastD;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by mijin on 2017-11-29.
 */

public class GroupSettingScheduleActivity2 extends AppCompatActivity {
    MaterialNumberPicker startYear, startMonth, startDay, startHour, endYear, endMonth, endDay, endHour;
    TextView startText, endText;
    LinearLayout eventName;
    String url, id, project_id, sTime, eTime;
    ContentValues values;
    int t, d, m, y, t2, d2, m2, y2;
    NetworkTask30 networkTask30;
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat ddformat = new SimpleDateFormat("yyyy-M-d");
    Date to, from, settingTo, settingFrom;
    Calendar cal = Calendar.getInstance();
    Calendar calendar;

    Intent in, intent;
    PendingIntent sender;

    AlarmManager am;
    CheckBox alarm;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting_schedule2);

        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        in = getIntent();

        SharedPreferences prefs = getSharedPreferences("PrefName",MODE_PRIVATE);
        id = prefs.getString("id",null);
        project_id = prefs.getString("project_id",null);

        try {
            to = ddformat.parse(prefs.getString("start",null));
            from = ddformat.parse(prefs.getString("end",null));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        eventName = (LinearLayout) findViewById(R.id.eventName);
        eventName.setVisibility(View.GONE);

        startText = (TextView) findViewById(R.id.startText);
        endText = (TextView) findViewById(R.id.endText);

        startYear = (MaterialNumberPicker) findViewById(R.id.startYear);
        startMonth = (MaterialNumberPicker) findViewById(R.id.startMonth);
        startDay = (MaterialNumberPicker) findViewById(R.id.startDay);
        startHour = (MaterialNumberPicker) findViewById(R.id.startHour);



        endYear = (MaterialNumberPicker) findViewById(R.id.endYear);
        endMonth = (MaterialNumberPicker) findViewById(R.id.endMonth);
        endDay = (MaterialNumberPicker) findViewById(R.id.endDay);
        endHour = (MaterialNumberPicker) findViewById(R.id.endHour);



        startYear.setValue(cal.get(Calendar.YEAR));
        startMonth.setValue(cal.get(Calendar.MONTH)+1);
        calendar = new GregorianCalendar(startYear.getValue(),startMonth.getValue()-1,1);
        startDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        startDay.setValue(cal.get(Calendar.DATE));
        startHour.setValue(1);

        startText.setText(startYear.getValue()+"년"+startMonth.getValue()+"월"+startDay.getValue()+"일 "+startHour.getValue()+"시");

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

        endText.setText(endYear.getValue()+"년"+endMonth.getValue()+"월"+endDay.getValue()+"일 "+endHour.getValue()+"시");

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

        alarm = (CheckBox) findViewById(R.id.alarm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                d = startDay.getValue();
                m = startMonth.getValue();
                y = startYear.getValue();
                //t = startTime.getNewCurrentTime().getHourOfDay();
                t = startHour.getValue();
                sTime = y+"-"+m+"-"+d+" "+t+":00:00";

                if(alarm.isChecked()){
                    intent = new Intent(GroupSettingScheduleActivity2.this, BroadcastD.class);
                    intent.putExtra("isGroupSetting", true);
                    intent.putExtra("content", id + "님의 Project#" + project_id + " 일정");

                    sender = PendingIntent.getBroadcast(GroupSettingScheduleActivity2.this, 0, intent, FLAG_UPDATE_CURRENT);

                    Calendar calendar = Calendar.getInstance();
                    //알람시간 calendar에 set해주기

                    calendar.set(y, m, d, t, 0, 0);

                    //알람 예약
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

                } else {
                    am.cancel(sender);
                }


                d2 = endDay.getValue();
                m2 = endMonth.getValue();
                y2 = endYear.getValue();
                t2= endHour.getValue();
                //t2 = endTime.getNewCurrentTime().getHourOfDay();


                try {
                    settingTo = ddformat.parse(y + "-" + m + "-" + d);
                    settingFrom = ddformat.parse(y2 + "-" + m2 + "-" + d2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                if(settingTo.compareTo(to)>=0&&settingFrom.compareTo(to)>=0&&settingTo.compareTo(from)<=0&&(settingFrom.compareTo(from)<=0||(settingFrom.compareTo(from)==1&&t2==0))) {
                    url = "http://uoshue.dothome.co.kr/addGroupSchedule.php?";
                    values = new ContentValues();
                    values.put("id", id);
                    values.put("project_id", project_id);
                    values.put("start", sTime);

                    if (y2 < y) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity2.this, R.style.MyAlertDialog);
                        builder.setMessage("일정을 다시 확인해주세요.");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    } else if (y2 == y) {
                        if (m2 < m) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity2.this, R.style.MyAlertDialog);
                            builder.setMessage("일정을 다시 확인해주세요.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        } else if (m2 == m) {
                            if (d2 < d) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity2.this, R.style.MyAlertDialog);
                                builder.setMessage("일정을 다시 확인해주세요.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.show();
                            } else if (d2 == d) {
                                if (t2 <= t) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity2.this, R.style.MyAlertDialog);
                                    builder.setMessage("일정을 다시 확인해주세요.");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    builder.show();
                                } else {

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity2.this, R.style.MyAlertDialog);
                    builder.setMessage("프로젝트 일정을 벗어납니다. 일정을 다시 선택해주세요.");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupSettingScheduleActivity2.this,R.style.MyAlertDialog);
                    builder.setMessage("일정이 겹칩니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else if(result.equals("success")) {
                    /*
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("setting2", "ok");
                    intent.putExtra("project_id",project_id);
                    if (in != null) intent.putExtra("date", in.getStringExtra("date"));
                    startActivityForResult(intent, 2);
                    */
                    Intent returnintent = new Intent();
                    setResult(Activity.RESULT_OK,returnintent);
                    GroupSettingScheduleActivity2.this.finish();

                }
            }
        }
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK)
            finish();
    }
    */

}

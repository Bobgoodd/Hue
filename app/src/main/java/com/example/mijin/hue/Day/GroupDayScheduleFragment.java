package com.example.mijin.hue.Day;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by mijin on 2017-11-25.
 */

public class GroupDayScheduleFragment extends Fragment implements WeekView.EventLongPressListener, MonthLoader.MonthChangeListener, WeekView.ScrollListener{
    WeekView mWeekView;
    View view;
    String url, project_id, date, jstart, jend;
    ContentValues values;
    NetworkTask32 networkTask32;
    int eventId;
    Calendar day;
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ddformat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
    SimpleDateFormat dddformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    WeekViewEvent weekViewEvent, revent;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    Calendar cstart, cend, cal;
    Bundle b;
    RectF r;
    //boolean first = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group_day_schedule, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);
        project_id = prefs.getString("project_id",null);



        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.setDefaultEventColor(getResources().getColor(R.color.colorLogoTransparent));
        mWeekView.setShowDistinctPastFutureColor(true);

        b = getArguments();
        if(b!=null) {
            cal = Calendar.getInstance();
            try {
                cal.setTime(dformat.parse(b.getString("date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mWeekView.goToDate(cal);
        }

       load();

        FloatingActionButton floating = (FloatingActionButton) view.findViewById(R.id.floating);

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GroupSettingScheduleActivity2.class);
                intent.putExtra("date",b.getString("date"));
                startActivityForResult(intent,10);
            }
        });

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
        mWeekView.setMonthChangeListener(this);

        return view;
    }


    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    /*
     * Checks if an event falls into a specific year and month.
     * @param event The event to check for.
     * @param year The year.
     * @param month The month.
     * @return True if the event matches the year and month.
     */
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && (int)event.getStartTime().get(Calendar.MONTH) == month-1) || (event.getEndTime().get(Calendar.YEAR) == year && (int)event.getEndTime().get(Calendar.MONTH) == month - 1);
    }



    @Override
    public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
        day = newFirstVisibleDay;
        date = dformat.format(day);
    }




    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        eventId = (int) event.getId();
        revent = event;
        r = eventRect;
        if(event.getName().equals("project#"+project_id)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialog);
            builder.setMessage("일정을 삭제하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                /*
                url = "http://uoshue.dothome.co.kr/deleteDaySchedule.php";
                values = new ContentValues();
                values.put("eventId", eventId);
                values.put("id",id);
                values.put("date",date);

                networkTask31 = new NetworkTask31(url, values);
                networkTask31.execute();
                */

                    url = "http://uoshue.dothome.co.kr/deleteGroupDaySchedule.php?";
                    values = new ContentValues();
                    values.put("project_id", project_id);
                    values.put("start",ddformat.format(revent.getStartTime().getTime()));
                    values.put("end",dddformat.format(revent.getEndTime().getTime()));

                    networkTask32 = new NetworkTask32(url, values);
                    networkTask32.execute();

                    load();
                    mWeekView.notifyDatasetChanged();

                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.MyAlertDialog);
            builder.setMessage("삭제 권한이 없습니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }

    }

    public class NetworkTask32 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask32(String url, ContentValues values) {

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
            Calendar now = Calendar.getInstance();
            try {
                if(result!=null){
                    // if(first==true){
                    events.clear();

                    JSONArray jarr = new JSONArray(result);
                    for(int i=0;i<jarr.length();i++) {
                        JSONObject json = (JSONObject) jarr.get(i);

                        jstart = json.getString("start");
                        jend = json.getString("end");


                        Calendar cstart = (Calendar) now.clone();
                        cstart.setTime(ddformat.parse(jstart));

                        Calendar cend = (Calendar) now.clone();
                        cend.setTime(dddformat.parse(jend));

                        // Create an week view event.
                        if(!json.getString("project_id").equals("")&&json.getString("project_id").equals(project_id)){
                            weekViewEvent = new WeekViewEvent(json.getLong("eventId"), "project#"+json.getString("project_id"), cstart, cend);
                            weekViewEvent.setColor(getResources().getColor(R.color.color33));
                        }else {
                            weekViewEvent = new WeekViewEvent(json.getLong("eventId"), "", cstart, cend);
                        }

                        events.add(weekViewEvent);
                        //  }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            for(int j=0;j<events.size();j++){
                Log.d("뭐야",events.get(j)+"/"+j);
            }
            mWeekView.notifyDatasetChanged();
        }
    }

    public void load(){
        url = "http://uoshue.dothome.co.kr/loadGroupDaySchedule.php?";
        values = new ContentValues();
        values.put("project_id",project_id);
        //values.put("date",date);


        networkTask32 = new NetworkTask32(url, values);
        networkTask32.execute();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode==10&&resultCode==RESULT_OK)
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }


}

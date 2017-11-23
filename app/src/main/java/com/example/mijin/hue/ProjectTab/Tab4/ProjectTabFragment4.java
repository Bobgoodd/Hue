package com.example.mijin.hue.ProjectTab.Tab4;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabFragment4 extends Fragment {

    MaterialCalendarView materialCalendarView;
    SharedPreferences prefs;
    String url;
    ContentValues values;
    NetworkTask17 networkTask17;
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tab2 = inflater.inflate(R.layout.fragment_login_tab2, container, false);

        prefs = getActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);
        materialCalendarView = (MaterialCalendarView) tab2.findViewById(R.id.calendarView);


        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 10, 1))
                .setMaximumDate(CalendarDay.from(2017, 10, 30))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorator(new SundayDecorator());
        materialCalendarView.addDecorator(new SaturdayDecorator());
        materialCalendarView.addDecorator(new OneDayDecorator());

        url = "http://uoshue.dothome.co.kr/loadProjectSchedule.php?";
        values = new ContentValues();
        values.put("project_id", prefs.getString("project_id",null));

        networkTask17 = new NetworkTask17(url, values);
        networkTask17.execute();


/*
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                ArrayList<CalendarDay> calendarDays = new ArrayList<CalendarDay>();
                calendarDays.add(date);
                ApiSimulator apiSimulator = new ApiSimulator(widget,calendarDays);
                apiSimulator.execute();


                String new_date = dformat.format(date.getDate());
                Log.d("날짜형식2",new_date);


                url = "http://uoshue.dothome.co.kr/addSchedule.php?";
                values = new ContentValues();
                values.put("id",prefs.getString("id",null));
                values.put("date",new_date);
                networkTask17 = new NetworkTask17(url,values);
                networkTask17.execute();

            }
        });
*/
        return tab2;


    }

    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        private MaterialCalendarView materialCalendarView;
        private ArrayList<CalendarDay> date;
        public ApiSimulator(MaterialCalendarView materialCalendarView, ArrayList<CalendarDay> date) {
            this.materialCalendarView = materialCalendarView;
            this.date = date;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for(CalendarDay calendarDay : date) {
                dates.add(calendarDay);
                calendar.add(Calendar.DATE, 1);
            }
            /*
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }
            */
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);



            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays));
            //widget.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }




    /**
     * Decorate several days with a dot
     */
    public class EventDecorator implements DayViewDecorator {

        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(10, color));
        }
    }

    public class NetworkTask17 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask17(String url, ContentValues values) {

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
            if(result!=null){

                try {
                    ArrayList<CalendarDay> calendarDays = new ArrayList<CalendarDay>();

                    CalendarDay c = materialCalendarView.getMinimumDate();
                    Calendar cal = Calendar.getInstance();
                    while(c.isInRange(materialCalendarView.getMinimumDate(),materialCalendarView.getMaximumDate())){
                        calendarDays.add(c);
                        cal.setTime(c.getDate());
                        cal.add(Calendar.DATE,1);
                        c=CalendarDay.from(cal);
                    }
                    JSONArray jarr = new JSONArray(result);
                    for(int i=0;i<jarr.length();i++) {
                        JSONObject json = (JSONObject) jarr.get(i);

                        String before = json.getString("date");

                        try {
                            calendarDays.remove(CalendarDay.from(dformat.parse(before)));
                            Log.d("삭제",CalendarDay.from(dformat.parse(before)).toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                    ApiSimulator apiSimulator = new ApiSimulator(materialCalendarView, calendarDays);
                    apiSimulator.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

}
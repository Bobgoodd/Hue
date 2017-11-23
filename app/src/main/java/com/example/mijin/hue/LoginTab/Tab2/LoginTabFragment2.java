package com.example.mijin.hue.LoginTab.Tab2;

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
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
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

public class LoginTabFragment2 extends Fragment {
 /*   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tab2 = inflater.inflate(R.layout.fragment_login_tab2, container, false);

        return tab2;
    }
}

class DateTimePicker extends LinearLayout {

    public static interface OnDateTimeChangedListener {
        void onDateTimeChanged(DateTimePicker view, int year, int monthOfYear, int dayOfYear, int hourOfDay, int minute);
    }

    private OnDateTimeChangedListener listener;

    private DatePicker datePicker;

    private TimePicker timePicker;

    private CheckBox enableTimeCheckBox;

    public DateTimePicker(Context context) {
        super(context);
        init(context);
    }

    public DateTimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // XML 레이아웃을 인플레이션함
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_login_tab2, this, true);

        // 시간 정보 참조
        Calendar calendar = Calendar.getInstance();
        final int curYear = calendar.get(Calendar.YEAR);
        final int curMonth = calendar.get(Calendar.MONTH);
        final int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        final int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int curMinute = calendar.get(Calendar.MINUTE);

        // 날짜선택 위젯 초기화
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.init(curYear, curMonth, curDay, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 새로 정의한 리스너로 이벤트 전달
                // getHour(), getMinute() 메소드는 API 23부터 지원함
                if(listener != null){
                    listener.onDateTimeChanged(
                            DateTimePicker.this, year, monthOfYear, dayOfMonth,
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                }
            }
        });

        // 체크박스 이벤트 처리
        enableTimeCheckBox = (CheckBox)findViewById(R.id.enableTimeCheckBox);
        enableTimeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                timePicker.setEnabled(isChecked);
                timePicker.setVisibility((enableTimeCheckBox).isChecked()?View.VISIBLE:View.INVISIBLE);
            }
        });

        // 시간선택 위젯 이벤트 처리
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(listener != null) {
                    listener.onDateTimeChanged(
                            DateTimePicker.this, datePicker.getYear(),
                            datePicker.getMonth(), datePicker.getDayOfMonth(), hourOfDay, minute);
                }
            }
        });

        timePicker.setCurrentHour(curHour);
        timePicker.setCurrentMinute(curMinute);
        timePicker.setEnabled(enableTimeCheckBox.isChecked());
        timePicker.setVisibility((enableTimeCheckBox.isChecked()?View.VISIBLE:View.INVISIBLE));
    }

    public void setOnDateTimeChangedListener(OnDateTimeChangedListener dateTimeListener){
        this.listener = dateTimeListener;
    }

    public void updateDateTime(int year, int monthO1fYear, int dayOfMonth, int currentHour, int currentMinute){
        //datePicker.updateDate(year, monthOfYear, dayOfMonth);
        timePicker.setCurrentHour(currentHour);
        timePicker.setCurrentMinute(currentMinute);
    }

    public void updateDate(int year, int monthOfYear, int dayOfMonth){
        datePicker.updateDate(year, monthOfYear, dayOfMonth);
    }

    public void setIs24HourView(final boolean is24HourView){
        timePicker.setIs24HourView(is24HourView);
    }

    public int getYear() {
        return datePicker.getYear();
    }

    public int getMonth() {
        return datePicker.getMonth();
    }

    public int getDayOfMonth() {
        return datePicker.getDayOfMonth();
    }

    public int getCurrentHour() {
        return timePicker.getCurrentHour();
    }

    public int getCurrentMinute() {
        return timePicker.getCurrentMinute();
    }

    public boolean enableTime() {
        return enableTimeCheckBox.isChecked();
    }
}
*/
    MaterialCalendarView materialCalendarView;
    SharedPreferences prefs;
    String url;
    ContentValues values;
    NetworkTask16 networkTask16;
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tab2 = inflater.inflate(R.layout.fragment_login_tab2, container, false);

        prefs = getActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);
        materialCalendarView = (MaterialCalendarView) tab2.findViewById(R.id.calendarView);


        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorator(new SundayDecorator());
        materialCalendarView.addDecorator(new SaturdayDecorator());
        materialCalendarView.addDecorator(new OneDayDecorator());

        url = "http://uoshue.dothome.co.kr/loadSchedule.php?";
        values = new ContentValues();
        values.put("id", prefs.getString("id",null));

        networkTask16 = new NetworkTask16(url, values);
        networkTask16.execute();

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
                networkTask16 = new NetworkTask16(url,values);
                networkTask16.execute();

            }
        });

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



            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
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

    public class NetworkTask16 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask16(String url, ContentValues values) {

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
                    JSONArray jarr = new JSONArray(result);
                    for(int i=0;i<jarr.length();i++) {
                        JSONObject json = (JSONObject) jarr.get(i);

                        String before = json.getString("date");

                        try {
                            calendarDays.add(CalendarDay.from(dformat.parse(before)));
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
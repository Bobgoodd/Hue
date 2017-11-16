package com.example.mijin.hue.ProjectTab.Tab2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mijin on 2017-10-03.
 */

public class DocumentDetailActivity extends AppCompatActivity implements DocumentDetailEditFragment.OnMyListener, DocumentDetailDefaultFragment.OnMyListener2 {
    String content, keyword;
    TextView createdTime, modifiedTime;
    Bundle b = new Bundle();
    Fragment fragment;
    String url;
    ContentValues values;
    NetworkTask11 networkTask11;
    Intent intent;
    ArrayList<String> labels = new ArrayList<String>();
    String project_id, document_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_documentdetail);

        intent = getIntent();
        project_id = intent.getStringExtra("project_id");
        document_id = intent.getStringExtra("document_id");

        b.putString("project_id",project_id);
        b.putString("document_id",document_id);

        fragment = new DocumentDetailDefaultFragment();
        fragment.setArguments(b);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.documentFragment, fragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(5f, 0));
        entries.add(new Entry(3f, 1));
        entries.add(new Entry(2f, 2));
        PieDataSet dataSet = new PieDataSet(entries,"KEYWORD");

        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        labels.add(0,"");
        labels.add(1,"");
        labels.add(2,"");


        PieData data = new PieData(labels, dataSet);
        pieChart.setData(data);


        url = "http://uoshue.dothome.co.kr/loadDocumentContent.php?";
        values = new ContentValues();

        values.put("project_id",project_id);
        values.put("document_id",document_id);
        networkTask11 = new NetworkTask11(url, values);
        networkTask11.execute();



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);




        createdTime = (TextView) findViewById(R.id.ddcreatedTime);
        modifiedTime = (TextView) findViewById(R.id.ddmodifiedTime);






    }



    public class NetworkTask11 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask11(String url, ContentValues values) {

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

            try {
                if (result != null) {

                    JSONArray jarr = new JSONArray(result);

                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = (JSONObject) jarr.get(i);


                        /*
                        b = new Bundle();
                        b.putString("content",content);
                        fragment.setArguments(b);
                        */


                        keyword = json.getString("keyword");
                        String[] split = keyword.split(",");


                        labels.clear();
                        labels.add(0,split[0]);
                        labels.add(1,split[1]);
                        labels.add(2,split[2]);

                        createdTime.setText(json.getString("created"));
                        modifiedTime.setText(json.getString("updated"));

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }



    public void onReceivedData(String[] split, String updated){
        labels.clear();
        for(int i=0;i<split.length;i++){
            labels.add(i,split[i]);
        }
        modifiedTime.setText(updated);
        changeFragment(1, b);
    }

    @Override
    public void onReceivedData2(String content) {
        this.content = content;
        b.putString("content",content);
        changeFragment(0, b);
    }

    public void changeFragment(int flag, Bundle b){
        if(flag==0){
            fragment = new DocumentDetailEditFragment();
            fragment.setArguments(b);
        }else{
            fragment = new DocumentDetailDefaultFragment();
            fragment.setArguments(b);
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.documentFragment, fragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}

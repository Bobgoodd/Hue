package com.example.mijin.hue.ProjectTab.Tab2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.mijin.hue.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mijin on 2017-10-03.
 */

public class DocumentDetailActivity extends AppCompatActivity {
    int flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentdetail);

        flag = 0;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        PieDataSet dataSet = new PieDataSet(entries,"what?");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        PieData data = new PieData(labels, dataSet);
        pieChart.setData(data);


        TextView createdTime = (TextView) findViewById(R.id.ddcreatedTime);
        TextView modifiedTime = (TextView) findViewById(R.id.ddmodifiedTime);

        createdTime.setText(new Date().toString());
        modifiedTime.setText(new Date().toString());

        Fragment fragment = new DocumentDetailDefaultFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.documentFragment, fragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        final Button rwbtn = (Button) findViewById(R.id.rwbtn);

        rwbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(flag==0){
                    rwbtn.setBackground(null);
                    rwbtn.setBackgroundColor(getResources().getColor(R.color.colorBack));
                    rwbtn.setText("저장");
                    rwbtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    rwbtn.setTextSize(10);
                    ChangeFragment(view, flag);
                    flag=1;
                }else{
                    rwbtn.setBackground(getDrawable(R.drawable.rewrite));
                    rwbtn.setText(null);
                    ChangeFragment(view, flag);
                    flag=0;
                }

            }
        });

    }

    public void ChangeFragment( View v , int flag) {

        Fragment fragment;

        switch( flag ) {
            default:
            case 0: {
                fragment = new DocumentDetailEditFragment();
                break;
            }
            case 1: {
                fragment = new DocumentDetailDefaultFragment();
                break;
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.documentFragment, fragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

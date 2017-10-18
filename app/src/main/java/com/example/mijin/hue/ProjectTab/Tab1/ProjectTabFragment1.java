package com.example.mijin.hue.ProjectTab.Tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mijin.hue.R;

/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabFragment1 extends Fragment {
    Button sound, mic;
    int soundflag, micflag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View tab1 = inflater.inflate(R.layout.fragment_project_tab1, container, false);

        /*
        ListView listView = (ListView) tab1.findViewById(R.id.participationList);
        ParticipationViewAdapter adapter;

        adapter = new ParticipationViewAdapter();

        listView.setAdapter(adapter);
        */

        RecyclerView recyclerView = (RecyclerView) tab1.findViewById(R.id.participationList);
        ParticipationViewAdapter adapter;

        adapter = new ParticipationViewAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tab1.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.addItem(R.drawable.man, "dd");
        adapter.addItem(R.drawable.woman, "wdd");
        adapter.addItem(R.drawable.man, "edd");
        adapter.addItem(R.drawable.man, "fdd");
        adapter.addItem(R.drawable.woman, "gdd");
        adapter.addItem(R.drawable.man, "hdd");
        adapter.addItem(R.drawable.man, "jdd");
        adapter.addItem(R.drawable.man, "kdd");
        adapter.addItem(R.drawable.woman, "ldd");


        adapter.notifyDataSetChanged();

        sound = (Button) tab1.findViewById(R.id.sound);
        mic = (Button) tab1.findViewById(R.id.mic);

        soundflag = 0;
        micflag = 0;

        sound.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(soundflag==0){
                    sound.setBackground(getResources().getDrawable(R.drawable.sound_on));
                    soundflag = 1;
                }else{
                    sound.setBackground(getResources().getDrawable(R.drawable.sound_off));
                    soundflag = 0;
                }

            }
        });

        mic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(micflag==0){
                    mic.setBackground(getResources().getDrawable(R.drawable.mic_on));
                    micflag = 1;
                }else{
                    mic.setBackground(getResources().getDrawable(R.drawable.mic_off));
                    micflag = 0;
                }
            }
        });

        return tab1;
    }
}

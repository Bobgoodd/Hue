package com.example.mijin.hue.ProjectTab.Tab1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mijin on 2017-10-03.
 */

public class ProjectTabFragment1 extends Fragment {
    Button sound, mic;
    int soundflag, micflag;
    ParticipationViewAdapter adapter;

    public interface OnMyListener{
        void onRecievedData(Object data);
    }

    private OnMyListener onMyListener;

    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity()!=null&& getActivity() instanceof OnMyListener){
            onMyListener = (OnMyListener) getActivity();
        }

    }

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


        adapter = new ParticipationViewAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tab1.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        String url = "http://uoshue.dothome.co.kr/loadParticipation.php?";
        ContentValues values = new ContentValues();

/*
        String project_id = getArguments().getString("project_id");

        Toast.makeText(getContext(), project_id,Toast.LENGTH_LONG).show();
        values.put("project_id",project_id);
*/

        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName",Context.MODE_PRIVATE);
        String project_id = prefs.getString("project_id",null);
        Toast.makeText(getContext(), project_id,Toast.LENGTH_LONG).show();
        values.put("project_id",project_id);
        NetworkTask7 networkTask7 = new NetworkTask7(url, values);
        networkTask7.execute();

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


        Button bb = (Button) tab1.findViewById(R.id.bb);

        bb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(onMyListener!=null) onMyListener.onRecievedData("데이터");
            }
        });


            return tab1;
    }

    public class NetworkTask7 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask7(String url, ContentValues values) {

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
                if(result!=null) {
                    JSONArray jarr = new JSONArray(result);

                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = (JSONObject) jarr.get(i);
                        // 접근한 회원테이블에 id가 있는지 있으면 그 튜플 반환
                        // 튜플로부터 데이터를 뽑아 어댑터에 추가
                        JSONArray memlist = (JSONArray) json.get("memlist");
                        for(int k=0;k<memlist.length();k++) {
                            adapter.addItem(R.drawable.man, String.valueOf(memlist.getString(k)));
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();

        }
    }
}

package com.example.mijin.hue;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mijin on 2017-11-19.
 */

public class MainFragment extends Fragment {
    TextView idMain, projectNo, friendNo, cardTitle1, cardCreated1, cardTitle2, cardCreated2, cardTitle3, cardCreated3;
    String url, id;
    ContentValues values;
    CardView card1, card2, card3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);
        id = prefs.getString("id",null);

        idMain = (TextView) view.findViewById(R.id.idMain);
        idMain.setText(id);

        projectNo = (TextView) view.findViewById(R.id.projectNo);
        friendNo = (TextView) view.findViewById(R.id.friendNo);
        cardTitle1 = (TextView) view.findViewById(R.id.cardTitle1);
        cardCreated1 = (TextView) view.findViewById(R.id.cardCreated1);
        cardTitle2 = (TextView) view.findViewById(R.id.cardTitle2);
        cardCreated2 = (TextView) view.findViewById(R.id.cardCreated2);
        cardTitle3 = (TextView) view.findViewById(R.id.cardTitle3);
        cardCreated3 = (TextView) view.findViewById(R.id.cardCreated3);
        card1 = (CardView) view.findViewById(R.id.card1);
        card2 = (CardView) view.findViewById(R.id.card2);
        card3 = (CardView) view.findViewById(R.id.card3);


        url = "http://uoshue.dothome.co.kr/loadInformation.php?";
        values = new ContentValues();
        values.put("id",id);

        NetworkTask30 networkTask30 = new NetworkTask30(url,values);
        networkTask30.execute();

        return view;
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

            try {
                if(result!=null&&!result.equals("")) {


                        JSONObject json = new JSONObject(result);
                        // 접근한 회원테이블에 id가 있는지 있으면 그 튜플 반환
                        // 튜플로부터 데이터를 뽑아 어댑터에 추가

                        projectNo.setText(String.valueOf(json.getInt("projectNo")));
                        friendNo.setText(String.valueOf(json.getInt("friendNo")));

                        JSONArray jarr2 = (JSONArray) json.get("current");
                        switch(jarr2.length()){
                            case 0:
                                break;

                            case 1:
                                card1.setVisibility(View.VISIBLE);
                                if(((JSONObject) jarr2.get(0)).getInt("project_id")==0) cardTitle1.setText(((JSONObject) jarr2.get(0)).getString("title"));
                                else cardTitle1.setText("Project#"+((JSONObject) jarr2.get(0)).getInt("project_id"));
                                cardCreated1.setText(((JSONObject) jarr2.get(0)).getString("updated"));
                                break;

                            case 2:
                                card1.setVisibility(View.VISIBLE);
                                card2.setVisibility(View.VISIBLE);
                                if(((JSONObject) jarr2.get(0)).getInt("project_id")==0) cardTitle1.setText(((JSONObject) jarr2.get(0)).getString("title"));
                                else cardTitle1.setText("Project#"+((JSONObject) jarr2.get(0)).getInt("project_id"));
                                if(((JSONObject) jarr2.get(1)).getInt("project_id")==0) cardTitle2.setText(((JSONObject) jarr2.get(1)).getString("title"));
                                else cardTitle1.setText("Project#"+((JSONObject) jarr2.get(1)).getInt("project_id"));
                                cardCreated1.setText(((JSONObject) jarr2.get(0)).getString("updated"));
                                cardCreated2.setText(((JSONObject) jarr2.get(1)).getString("updated"));
                                break;

                            case 3:
                                card1.setVisibility(View.VISIBLE);
                                card2.setVisibility(View.VISIBLE);
                                card3.setVisibility(View.VISIBLE);
                                if(((JSONObject) jarr2.get(0)).getInt("project_id")==0) cardTitle1.setText(((JSONObject) jarr2.get(0)).getString("title"));
                                else cardTitle1.setText("Project#"+((JSONObject) jarr2.get(0)).getInt("project_id"));
                                if(((JSONObject) jarr2.get(1)).getInt("project_id")==0) cardTitle2.setText(((JSONObject) jarr2.get(1)).getString("title"));
                                else cardTitle1.setText("Project#"+((JSONObject) jarr2.get(1)).getInt("project_id"));
                                if(((JSONObject) jarr2.get(2)).getInt("project_id")==0) cardTitle3.setText(((JSONObject) jarr2.get(2)).getString("title"));
                                else cardTitle1.setText("Project#"+((JSONObject) jarr2.get(2)).getInt("project_id"));

                                cardCreated1.setText(((JSONObject) jarr2.get(0)).getString("updated"));
                                cardCreated2.setText(((JSONObject) jarr2.get(1)).getString("updated"));
                                cardCreated3.setText(((JSONObject) jarr2.get(2)).getString("updated"));
                                break;
                        }




                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

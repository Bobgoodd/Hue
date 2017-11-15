package com.example.mijin.hue.LoginTab.Tab3;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mijin on 2017-10-03.
 */

public class LoginTabFragment3 extends Fragment{


    ListView listView;
    FriendViewAdapter adapter;
    EditText friendId;
    String url;
    ContentValues values;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tab3 = inflater.inflate(R.layout.fragment_login_tab3, container, false);





        friendId = (EditText) tab3.findViewById(R.id.friendId);


        friendId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button search;
        com.melnykov.fab.FloatingActionButton fabb;



        adapter = new FriendViewAdapter();



        listView = (ListView) tab3.findViewById(R.id.friendList);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        url =  "http://uoshue.dothome.co.kr/loadFriend.php?";
        values = new ContentValues();
        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName",MODE_PRIVATE);
        values.put("usr_id",prefs.getString("id",null));
        NetworkTask2 networkTask2 = new NetworkTask2(url,values);
        networkTask2.execute();

        /*
        if(getArguments()!=null) {
            FriendViewItem item = getArguments().getParcelable("new");
            if (item != null)
                adapter.addItem(item.getProfile(), item.getId(), item.getName(), item.getEmail(), item.getPhone());

            adapter.notifyDataSetChanged();
        }
        */
        search = (Button) tab3.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               adapter.getFilter().filter((CharSequence)friendId.getText().toString());
            }
        });


        fabb = (com.melnykov.fab.FloatingActionButton) tab3.findViewById(R.id.fabb);
        fabb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewFriendActivity.class);
                startActivity(intent);
            }
        });



        return tab3;
    }


    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask2(String url, ContentValues values) {

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
                        adapter.addItem(R.drawable.man, json.getString("id"), json.getString("name"), json.getString("email"), "010-3315-4444");

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();

        }
    }


}

package com.example.mijin.hue.LoginTab.Tab3;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.mijin.hue.R;

/**
 * Created by mijin on 2017-10-03.
 */

public class LoginTabFragment3 extends Fragment{


    //static boolean flag=false;
    //static Fragment f;
    ListView listView;
    FriendViewAdapter adapter, adapter1;
    EditText friendId;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tab3 = inflater.inflate(R.layout.fragment_login_tab3, container, false);


        friendId = (EditText) tab3.findViewById(R.id.friendId);
        friendId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FriendViewItem friendViewItem;
                int fprofile, j;
                String fid, fname, femail, fphone;

                if (friendId.isFocusable()) {
                    adapter1.clear();
                    listView.setAdapter(adapter1);

                    for (j = 0; j < adapter.getCount(); j++) {
                        friendViewItem = (FriendViewItem) adapter.getItem(j);
                        if (friendViewItem.getId().contains(friendId.getText().toString())) {
                            fprofile = friendViewItem.getProfile();
                            fid = friendViewItem.getId();
                            fname = friendViewItem.getName();
                            femail = friendViewItem.getEmail();
                            fphone = friendViewItem.getPhone();

                            adapter1.addItem(fprofile, fid, fname, femail, fphone);
                        }
                    }

                    adapter1.notifyDataSetChanged();
                }else{
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button search;
        com.melnykov.fab.FloatingActionButton fabb;



        adapter = new FriendViewAdapter();
        adapter1 = new FriendViewAdapter();


        listView = (ListView) tab3.findViewById(R.id.friendList);
        listView.setAdapter(adapter);

        Bundle b = getArguments();
        if(b!=null&&b.getParcelable("new")!=null){

            FriendViewItem item = (FriendViewItem) b.getParcelable("new");

            Toast.makeText(tab3.getContext(),item.getId(),Toast.LENGTH_LONG).show();
            adapter.addItem(item.getProfile(), item.getId(), item.getName(), item.getEmail(), item.getPhone());

            b.clear();
        }

        adapter.addItem(R.drawable.man, "dd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.woman, "wdd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.man, "edd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.man, "fdd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.woman, "gdd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.man, "hdd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.man, "jdd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.man, "kdd", "김동수", "dd@naver.com", "010-3315-4444");
        adapter.addItem(R.drawable.woman, "ldd", "김동수", "dd@naver.com", "010-3315-4444");

        adapter.notifyDataSetChanged();

        search = (Button) tab3.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = friendId.getText().toString();
                FriendViewItem friendViewItem;
                int fprofile, j;
                String fid, fname, femail, fphone;



                if (id != null){
                    adapter1.clear();
                    listView.setAdapter(adapter1);

                for (j = 0; j < adapter.getCount(); j++) {
                    friendViewItem = (FriendViewItem) adapter.getItem(j);
                    if (friendViewItem.getId().contains(id)) {
                        fprofile = friendViewItem.getProfile();
                        fid = friendViewItem.getId();
                        fname = friendViewItem.getName();
                        femail = friendViewItem.getEmail();
                        fphone = friendViewItem.getPhone();

                        adapter1.addItem(fprofile, fid, fname, femail, fphone);
                    }
                }

                adapter1.notifyDataSetChanged();
                }else{
                    listView.setAdapter(adapter);
                }

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



    /*
    public static Fragment newInstance(FriendViewItem item){
        f = new LoginTabFragment3();
        Bundle b = new Bundle();
        b.putParcelable("new", item);
        f.setArguments(b);
        flag=true;
        return f;
    }
    */


}

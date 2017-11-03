package com.example.mijin.hue.LoginTab.Tab1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mijin.hue.LoginTab.LoginTabActivity;
import com.example.mijin.hue.LoginTab.Tab3.FriendViewAdapter;
import com.example.mijin.hue.LoginTab.Tab3.FriendViewItem;
import com.example.mijin.hue.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mijin on 2017-10-31.
 */

public class AddProjectActivity extends AppCompatActivity {
    ArrayList<FriendViewItem> mem = new ArrayList<FriendViewItem>();
    ListView listView;
    FriendViewAdapter ad, adapter, adapter1;
    EditText friendId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_tab3);

        adapter = new FriendViewAdapter();
        adapter1 = new FriendViewAdapter();


        listView = (ListView) findViewById(R.id.friendList);
        listView.setAdapter(adapter);
        ad = adapter;
        adapter.setFlag(true);


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


        Button search = (Button) findViewById(R.id.search);
        search.setText("추가");
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(mem.size()==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("멤버를 선택해주세요.");
                    builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else {
                    Intent intent = new Intent(getApplicationContext(), LoginTabActivity.class);
                    intent.putParcelableArrayListExtra("mem", mem);
                    startActivityForResult(intent, 0);
                }
            }
        });

        com.melnykov.fab.FloatingActionButton fabb = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fabb);
        fabb.setVisibility(View.INVISIBLE);


        friendId = (EditText) findViewById(R.id.friendId);
        friendId.setFocusable(false);

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
                            ad = adapter1;
                        }
                    }

                    adapter1.notifyDataSetChanged();
                }else{
                    listView.setAdapter(adapter);
                    ad = adapter;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listView.setOnItemClickListener(new ListView.OnItemClickListener(){

            CheckBox ch;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 ch = (CheckBox) view.findViewById(R.id.ch);

                if(mem.contains((FriendViewItem)adapterView.getAdapter().getItem(i))) {
                    mem.remove((FriendViewItem) adapterView.getAdapter().getItem(i));
                    mem.removeAll(Collections.singleton(null));
                    Toast.makeText(getApplicationContext(), ((FriendViewItem) adapterView.getAdapter().getItem(i)).getId()+" remove", Toast.LENGTH_LONG).show();
                    ch.setChecked(false);
                }else{
                    mem.add((FriendViewItem)adapterView.getAdapter().getItem(i));
                    Toast.makeText(getApplicationContext(), ((FriendViewItem) adapterView.getAdapter().getItem(i)).getId()+" add", Toast.LENGTH_LONG).show();
                    ch.setChecked(true);
                }

            }
        });
        /*
        listView.setOnItemSelectedListener(new ListView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mem.contains((FriendViewItem)adapterView.getAdapter().getItem(i))) {
                    mem.remove((FriendViewItem) adapterView.getAdapter().getItem(i));
                    Toast.makeText(getApplicationContext(), ((FriendViewItem) adapterView.getAdapter().getItem(i)).getId()+" remove", Toast.LENGTH_LONG).show();
                }else{
                    mem.add((FriendViewItem)adapterView.getAdapter().getItem(i));
                    Toast.makeText(getApplicationContext(), ((FriendViewItem) adapterView.getAdapter().getItem(i)).getId()+" add", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&requestCode==RESULT_OK)
            finish();
    }
/*
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SparseBooleanArray bool = listView.getCheckedItemPositions();
        if(bool!=null) {
            for (int i = 0; i < bool.size(); i++) {
                if (bool.get(i)) mem.add((FriendViewItem) adapter.getItem(i));
            }
        }
    }
    */
}

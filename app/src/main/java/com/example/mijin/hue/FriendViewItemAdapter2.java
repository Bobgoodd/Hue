package com.example.mijin.hue;

/**
 * Created by mijin on 2017-12-06.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mijin.hue.LoginTab.Tab3.FriendViewItem;

import java.util.ArrayList;

/**
 * Created by mijin on 2017-10-22.
 */

public class FriendViewItemAdapter2 extends BaseAdapter implements Filterable {

    private ArrayList<FriendViewItem> friendViewList = new ArrayList<FriendViewItem>();
    private ArrayList<FriendViewItem> filteredViewList =friendViewList;
    int index;
    boolean flag = false;
    CheckBox ch = null;
    Filter filter;

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<FriendViewItem> values = new ArrayList<FriendViewItem>();

                    if(constraint == null || constraint.length() == 0){
                        results.values = friendViewList;
                        results.count = friendViewList.size();
                    }else{
                        for(FriendViewItem rest : friendViewList){
                            if(rest.getId().contains(constraint))
                                values.add(rest);
                        }

                        results.values = values;
                        results.count = values.size();
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredViewList = (ArrayList<FriendViewItem>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        return filter;
    }

    @Override
    public int getCount() {
        return filteredViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredViewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.friendview_item, viewGroup, false);
            view.findViewById(R.id.ch).setVisibility(View.GONE);

        }



        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView friendProfile = (ImageView) view.findViewById(R.id.friendProfile);
        TextView friendId = (TextView) view.findViewById(R.id.friendId);
        //TextView friendName = (TextView) view.findViewById(R.id.friendName);
        //TextView friendEmail = (TextView) view.findViewById(R.id.friendEmail);
        //TextView friendPhone = (TextView) view.findViewById(R.id.friendPhone);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        FriendViewItem friendViewItem = filteredViewList.get(i);


        // 아이템 내 각 위젯에 데이터 반영
        //friendProfile.setImageResource(friendViewItem.getProfile());
        friendId.setText(friendViewItem.getId());
        //friendName.setText(friendViewItem.getName());
        //friendEmail.setText(friendViewItem.getEmail());
        //friendPhone.setText(friendViewItem.getPhone());

        ch = (CheckBox)view.findViewById(R.id.ch);

        if(flag==true){
            ch.setVisibility(View.VISIBLE);
        }

        return view;



    }

    public void addItem(int profile, String id, String name, String email, String phone) {
        FriendViewItem friendViewItem = new FriendViewItem();

        friendViewItem.setProfile(profile);
        friendViewItem.setId(id);
        friendViewItem.setName(name);
        friendViewItem.setEmail(email);
        friendViewItem.setPhone(phone);

        friendViewList.add(friendViewItem);
    }

    public void clear(){
        friendViewList.clear();
    }


    public void setFlag(boolean flag){
        this.flag = flag;
        notifyDataSetChanged();
    }



}
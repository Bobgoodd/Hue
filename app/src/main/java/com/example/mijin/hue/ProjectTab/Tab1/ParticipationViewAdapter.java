package com.example.mijin.hue.ProjectTab.Tab1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mijin.hue.R;

import java.util.ArrayList;

/**
 * Created by mijin on 2017-10-03.
 */
/*
public class ParticipationViewAdapter extends BaseAdapter {

    ArrayList<ParticipationViewItem> participationList = new ArrayList<ParticipationViewItem>();
    @Override

    public int getCount() {
        return participationList.size();
    }

    @Override
    public Object getItem(int i) {
        return participationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.projectview_item, viewGroup, false);
        }

        ImageView participationPhoto = (ImageView) view.findViewById(R.id.participationPhoto);
        TextView participationId = (TextView) view.findViewById(R.id.participationId);

        ParticipationViewItem participationViewItem = participationList.get(i);

        participationPhoto.setImageResource(participationViewItem.getPhoto());
        participationId.setText(participationViewItem.getId());

        return view;
    }

    public void addItem(int photo, String id){
        ParticipationViewItem participationViewItem = new ParticipationViewItem();

        participationViewItem.setPhoto(photo);
        participationViewItem.setId(id);

        participationList.add(participationViewItem);
    }
}
*/

public class ParticipationViewAdapter extends RecyclerView.Adapter {

    ArrayList<ParticipationViewItem> participationList = new ArrayList<ParticipationViewItem>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.participationview_item,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder holder1 = (ViewHolder) holder;
        ParticipationViewItem participationViewItem = participationList.get(position);

        holder1.participationPhoto.setImageResource(participationViewItem.getPhoto());
        holder1.participationId.setText(participationViewItem.getId());

    }

    @Override
    public int getItemCount() {
        return participationList.size();
    }

    public void addItem(int photo, String id){
        ParticipationViewItem participationViewItem = new ParticipationViewItem();

        participationViewItem.setPhoto(photo);
        participationViewItem.setId(id);

        participationList.add(participationViewItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView participationPhoto;
        public TextView participationId;

        public ViewHolder(View view) {
            super(view);
            participationPhoto = (ImageView) view.findViewById(R.id.participationPhoto);
            participationId = (TextView) view.findViewById(R.id.participationId);
        }
    }

}
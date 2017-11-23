package com.example.mijin.hue.ProjectTab.Tab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mijin.hue.R;

import java.util.ArrayList;


/**
 * Created by mijin on 2017-10-03.
 */

public class DocumentViewAdapter extends BaseAdapter{

    ArrayList<DocumentViewItem> documentViewList = new ArrayList<DocumentViewItem>();

    @Override
    public int getCount() {
        return documentViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return documentViewList.get(i);
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.documentview_item, viewGroup, false);
        }

        //TextView documentName = (TextView) view.findViewById(R.id.documentName);
        TextView createdTime = (TextView) view.findViewById(R.id.dcreatedTime);
        TextView modifiedTime = (TextView) view.findViewById(R.id.dmodifiedTime);
        TextView participationId = (TextView) view.findViewById(R.id.dparticipatedId);

        DocumentViewItem documentViewItem = documentViewList.get(i);

        //documentName.setText(documentViewItem.getDocumentName());
        createdTime.setText(documentViewItem.getCreatedTime());
        modifiedTime.setText(documentViewItem.getModifiedTime());
        participationId.setText(documentViewItem.getParticipationId());

        return view;
    }

    public void addItem(int documentId, String documentName, String createdTime, String modifiedTime, String participationId){
        DocumentViewItem documentViewItem = new DocumentViewItem();

        documentViewItem.setDocumentId(documentId);
        //documentViewItem.setDocumentName(documentName);
        documentViewItem.setCreatedTime(createdTime);
        documentViewItem.setModifiedTime(modifiedTime);
        documentViewItem.setParticipationId(participationId);

        documentViewList.add(documentViewItem);
    }
}
